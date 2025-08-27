package pragma.crediya.request.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;

@Repository
public interface LoanTypeJpaRepository extends ReactiveCrudRepository<LoanTypeEntity, Long> {
}
