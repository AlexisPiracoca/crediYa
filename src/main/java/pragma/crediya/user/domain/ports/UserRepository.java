package pragma.crediya.user.domain.ports;

import pragma.crediya.user.infrastructure.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<UserEntity> save(UserEntity userEntity);
    Flux<UserEntity> findAll();
}