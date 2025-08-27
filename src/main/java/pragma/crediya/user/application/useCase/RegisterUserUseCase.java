package pragma.crediya.user.application.useCase;

import org.springframework.stereotype.Service;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.domain.ports.UserRepository;
import pragma.crediya.user.infrastructure.mapper.UserMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepositoryPort;
    private final UserMapper userMapper;

    public RegisterUserUseCase(UserRepository userRepositoryPort, UserMapper userMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
    }

    public Mono<User> register(User user) {
        return userRepositoryPort.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El correo ya está en uso"));
                    }
                    return userRepositoryPort.save(userMapper.toEntity(user))
                            .map(userMapper::toDomain);
                });
    }

    public Flux<User> listAllUsers() {
        return userRepositoryPort.findAll()
                .map(userMapper::toDomain);
    }
}