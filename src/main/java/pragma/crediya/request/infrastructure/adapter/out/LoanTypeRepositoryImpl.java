package pragma.crediya.request.infrastructure.adapter.out;

import org.springframework.stereotype.Repository;
import pragma.crediya.request.domain.ports.LoanTypeRepository;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;
import pragma.crediya.request.infrastructure.repository.LoanTypeJpaRepository;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeRepositoryImpl implements LoanTypeRepository {

    private final LoanTypeJpaRepository jpaRepository;

    public LoanTypeRepositoryImpl(LoanTypeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Mono<LoanTypeEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }
}
