package pragma.crediya.user.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.user.infrastructure.entity.RolEntity;
import reactor.core.publisher.Mono;

@Repository
public interface RolJpaRepository extends ReactiveCrudRepository<RolEntity, Long> {
    Mono<RolEntity> findByName(String name);
}
