package pragma.crediya.request.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.RequestEntity;
import reactor.core.publisher.Mono;

@Repository
public interface RequestJpaRepository extends ReactiveCrudRepository<RequestEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
}
