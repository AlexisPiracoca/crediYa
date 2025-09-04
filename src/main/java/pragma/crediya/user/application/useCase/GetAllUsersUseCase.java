package pragma.crediya.user.application.useCase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.domain.ports.UserRepository;
import pragma.crediya.user.infrastructure.mapper.UserMapper;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class GetAllUsersUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetAllUsersUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Flux<User> execute() {
        log.info("Consultando todos los usuarios...");

        return userRepository.findAll()
                .doOnNext(user -> log.debug("Usuario encontrado: {}", user.getEmail()))
                .map(userMapper::toDomain)
                .doOnComplete(() -> log.info("Consulta de usuarios completada"));
    }
}
