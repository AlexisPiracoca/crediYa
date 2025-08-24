package pragma.crediya.request.domain.ports;

import pragma.crediya.request.domain.model.Request;
import reactor.core.publisher.Mono;

public interface RequestRepository {
    Mono<Request> save(Request request);
}
