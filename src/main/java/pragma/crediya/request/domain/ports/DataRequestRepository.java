package pragma.crediya.request.domain.ports;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.RequestEntitiy;
import reactor.core.publisher.Mono;

@Repository
public interface DataRequestRepository extends ReactiveCrudRepository<RequestEntitiy, Long> {
    Mono<Boolean> existsByEmail(String email);
}