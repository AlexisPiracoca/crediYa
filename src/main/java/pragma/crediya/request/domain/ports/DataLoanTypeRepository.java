package pragma.crediya.request.domain.ports;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;

@Repository
public interface DataLoanTypeRepository extends ReactiveCrudRepository<LoanTypeEntity, Long> {
}