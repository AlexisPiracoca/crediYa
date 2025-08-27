package pragma.crediya.request.infrastructure.adapter.out;

import org.springframework.stereotype.Repository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.infrastructure.entity.StatusEntity;
import pragma.crediya.request.infrastructure.repository.StatusJpaRepository;
import reactor.core.publisher.Mono;

@Repository
public class StatusRepositoryImpl implements StatusRepository {

    private final StatusJpaRepository jpaRepository;

    public StatusRepositoryImpl(StatusJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Mono<StatusEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }
}