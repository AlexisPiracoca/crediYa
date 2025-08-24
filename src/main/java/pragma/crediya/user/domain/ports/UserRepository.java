package pragma.crediya.user.domain.ports;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pragma.crediya.user.infrastructure.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
}