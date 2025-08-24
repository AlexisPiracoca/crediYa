package pragma.crediya.request.infrastructure.repository;

import org.springframework.stereotype.Repository;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.domain.ports.DataRequestRepository;
import pragma.crediya.request.infrastructure.entity.RequestEntitiy;
import reactor.core.publisher.Mono;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private final DataRequestRepository springDataRepository;

    public RequestRepositoryImpl(DataRequestRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Mono<Request> save(Request request) {
        RequestEntitiy entity = new RequestEntitiy(
                null,
                request.getAmount(),
                request.getTerm(),
                request.getEmail(),
                request.getStatus().getId(),
                request.getLoanType().getId()
        );

        return springDataRepository.save(entity)
                .map(saved -> {
                    request.setId(saved.getId());
                    return request;
                });
    }
}