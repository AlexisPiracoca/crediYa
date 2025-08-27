package pragma.crediya.request.infrastructure.adapter.out;

import org.springframework.stereotype.Repository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.infrastructure.entity.RequestEntity;
import pragma.crediya.request.infrastructure.repository.RequestJpaRepository;
import reactor.core.publisher.Mono;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private final RequestJpaRepository jpaRepository;

    public RequestRepositoryImpl(RequestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Mono<RequestEntity> save(RequestEntity requestEntity) {
        return jpaRepository.save(requestEntity);
    }
}
