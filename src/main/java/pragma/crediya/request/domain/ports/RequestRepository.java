package pragma.crediya.request.domain.ports;

import pragma.crediya.request.infrastructure.entity.RequestEntity;
import reactor.core.publisher.Mono;

public interface RequestRepository {
    Mono<Boolean> existsByEmail(String email);
    Mono<RequestEntity> save(RequestEntity requestEntity);
}