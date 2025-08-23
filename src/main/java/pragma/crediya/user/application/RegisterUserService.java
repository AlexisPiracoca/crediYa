package pragma.crediya.user.application;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pragma.crediya.user.domain.User;
import pragma.crediya.user.infrastructure.repository.UserEntity;
import pragma.crediya.user.infrastructure.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class RegisterUserService {

    private final UserRepository userRepository;

    public RegisterUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> register(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El correo ya está en uso"));
                    }
                    return userRepository.save(mapToEntity(user))
                            .map(this::mapToDomain);
                });
    }

    public Flux<User> listAllUsers() {
        return userRepository.findAll()
                .map(this::mapToDomain);
    }

    private User mapToDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getDateBirth(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getSalary()
        );
    }

    private UserEntity mapToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLastName(user.getLastName());
        entity.setDateBirth(user.getDateBirth());
        entity.setAddress(user.getAddress());
        entity.setPhone(user.getPhone());
        entity.setEmail(user.getEmail());
        entity.setSalary(user.getSalary());
        return entity;
    }
}
