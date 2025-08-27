package pragma.crediya.request.domain.ports;

import pragma.crediya.request.infrastructure.entity.StatusEntity;
import reactor.core.publisher.Mono;

public interface StatusRepository {
    Mono<StatusEntity> findById(Long id);
}