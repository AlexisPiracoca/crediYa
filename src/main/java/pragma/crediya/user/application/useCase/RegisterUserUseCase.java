package pragma.crediya.user.application.useCase;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.domain.ports.UserRepository;
import pragma.crediya.user.infrastructure.mapper.UserMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegisterUserUseCase {

    private final UserRepository userRepositoryPort;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // 👈 inyecta el encoder

    public RegisterUserUseCase(UserRepository userRepositoryPort,
                               UserMapper userMapper,
                               PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> register(User user) {
        log.info("Iniciando registro del usuario con email: {}", user.getEmail());

        return userRepositoryPort.existsByEmail(user.getEmail())
                .doOnNext(exists -> log.debug("¿Existe el usuario con email {}? {}", user.getEmail(), exists))
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Intento de registro con correo ya existente: {}", user.getEmail());
                        return Mono.error(new RuntimeException("El correo ya está en uso"));
                    }
                    log.info("Guardando nuevo usuario con email: {}", user.getEmail());

                    user.setPassword(passwordEncoder.encode(user.getPassword()));

                    return userRepositoryPort.save(userMapper.toEntity(user))
                            .doOnSuccess(saved -> log.info("Usuario guardado con ID: {}", saved.getId()))
                            .map(userMapper::toDomain);
                })
                .doOnError(error -> log.error("Error al registrar usuario con email {}: {}", user.getEmail(), error.getMessage()));
    }

    public Flux<User> listAllUsers() {
        log.info("Consultando lista de todos los usuarios...");
        return userRepositoryPort.findAll()
                .doOnNext(user -> log.debug("Usuario encontrado: {}", user))
                .map(userMapper::toDomain)
                .doOnComplete(() -> log.info("Consulta de usuarios completada."));
    }
}

