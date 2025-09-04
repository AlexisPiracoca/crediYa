package pragma.crediya.request.infrastructure.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;

@Repository
public interface LoanTypeJpaRepository extends R2dbcRepository<LoanTypeEntity, Long> {
}
