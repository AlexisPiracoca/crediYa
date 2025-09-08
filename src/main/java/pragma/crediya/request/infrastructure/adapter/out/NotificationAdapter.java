package pragma.crediya.request.infrastructure.adapter.out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pragma.crediya.request.domain.ports.NotificationPort;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NotificationAdapter implements NotificationPort {

    @Override
    public Mono<Void> notifyDecision(String email, String decision) {
        log.info("📧 Enviando notificación a {} con decisión: {}", email, decision);
        return Mono.empty();
    }
}
