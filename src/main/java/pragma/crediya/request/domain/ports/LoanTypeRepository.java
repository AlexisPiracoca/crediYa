package pragma.crediya.request.domain.ports;

import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanTypeEntity> findById(Long id);
}