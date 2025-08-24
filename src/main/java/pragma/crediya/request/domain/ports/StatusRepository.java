package pragma.crediya.request.domain.ports;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.infrastructure.entity.StatusEntity;

@Repository
public interface StatusRepository extends ReactiveCrudRepository<StatusEntity, Long> {
}