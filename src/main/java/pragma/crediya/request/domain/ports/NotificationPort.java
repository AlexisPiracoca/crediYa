package pragma.crediya.request.domain.ports;

import reactor.core.publisher.Mono;

public interface NotificationPort {
    Mono<Void> notifyDecision(String email, String decision);
}
