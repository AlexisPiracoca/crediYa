package pragma.crediya.user.infrastructure.adapter.out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pragma.crediya.user.domain.model.AuthenticationRequest;
import pragma.crediya.user.domain.model.AuthenticationResponse;
import pragma.crediya.user.domain.model.Rol;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.domain.ports.AuthenticationRepository;
import pragma.crediya.user.infrastructure.mapper.UserMapper;
import pragma.crediya.user.infrastructure.repository.RolJpaRepository;
import pragma.crediya.user.infrastructure.repository.UserJpaRepository;
import pragma.crediya.user.infrastructure.service.JwtService;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final UserJpaRepository userJpaRepository;
    private final RolJpaRepository rolJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public AuthenticationRepositoryImpl(UserJpaRepository userJpaRepository,
                                        RolJpaRepository rolJpaRepository,
                                        PasswordEncoder passwordEncoder,
                                        UserMapper userMapper,
                                        JwtService jwtService) {
        this.userJpaRepository = userJpaRepository;
        this.rolJpaRepository = rolJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public Mono<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return userJpaRepository.findByEmail(request.getEmail())
                .flatMap(userEntity -> {
                    log.info("=== Intentando autenticar ===");
                    log.info("Email request: {}", request.getEmail());
                    log.info("Password request (plano): {}", request.getPassword());
                    log.info("Password hash en BD: {}", userEntity.getPassword());

                    boolean matches = passwordEncoder.matches(request.getPassword(), userEntity.getPassword());
                    log.info("¿Passwords coinciden?: {}", matches);

                    if (matches) {
                        return rolJpaRepository.findById(userEntity.getRolId())
                                .map(rolEntity -> {
                                    User user = userMapper.toDomain(userEntity);
                                    Rol rol = new Rol(rolEntity.getId(), rolEntity.getName(), rolEntity.getDescription());
                                    user.setRol(rol);

                                    String token = jwtService.generateToken(user);
                                    log.info("✅ Autenticación exitosa para {}", request.getEmail());
                                    return new AuthenticationResponse(token, "Autenticación exitosa", true, user);
                                })
                                .switchIfEmpty(Mono.fromSupplier(() -> {
                                    User user = userMapper.toDomain(userEntity);
                                    String token = jwtService.generateToken(user);
                                    return new AuthenticationResponse(token, "Autenticación exitosa", true, user);
                                }));
                    } else {
                        log.warn("❌ Contraseña incorrecta para {}", request.getEmail());
                        return Mono.just(new AuthenticationResponse(null, "Credenciales inválidas", false, null));
                    }
                })
                .switchIfEmpty(Mono.just(new AuthenticationResponse(null, "Usuario no encontrado", false, null)));
    }

    @Override
    public Mono<Boolean> validateUser(String email, String password) {
        return userJpaRepository.findByEmail(email)
                .map(userEntity -> passwordEncoder.matches(password, userEntity.getPassword()))
                .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Mono<String> generateToken(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userEntity -> {
                    User user = userMapper.toDomain(userEntity);
                    return jwtService.generateToken(user);
                });
    }
}
