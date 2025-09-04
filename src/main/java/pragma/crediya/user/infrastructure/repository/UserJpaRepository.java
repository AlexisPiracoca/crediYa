package pragma.crediya.user.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.user.infrastructure.entity.UserEntity;
import reactor.core.publisher.Mono;

@Repository
public interface UserJpaRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
    Mono<UserEntity> findById(Long id);
    Mono<UserEntity> findByEmail(String email); // ⭐ Agregar este método
}
