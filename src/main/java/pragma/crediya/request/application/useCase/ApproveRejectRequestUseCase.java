package pragma.crediya.request.application.useCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.domain.ports.NotificationPort;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveRejectRequestUseCase {

    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;
    private final RequestMapper mapper;
    private final NotificationPort notificationPort;

    private static final Long STATUS_APROBADA = 2L;
    private static final Long STATUS_RECHAZADA = 3L;

    public Mono<Request> updateStatus(Long requestId, String decision) {
        log.info("🔄 Iniciando proceso de {} para solicitud {}", decision, requestId);

        Long statusId;
        if ("APROBADA".equalsIgnoreCase(decision)) {
            statusId = STATUS_APROBADA;
        } else if ("RECHAZADA".equalsIgnoreCase(decision)) {
            statusId = STATUS_RECHAZADA;
        } else {
            return Mono.error(new IllegalArgumentException("Decisión inválida: " + decision));
        }

        return requestRepository.findById(requestId)
                .switchIfEmpty(Mono.error(new RuntimeException("Solicitud no encontrada")))
                .flatMap(requestEntity ->
                        statusRepository.findById(statusId)
                                .flatMap(statusEntity -> {
                                    requestEntity.setStatusId(statusEntity.getId());

                                    return requestRepository.save(requestEntity)
                                            .map(savedEntity -> {
                                                Status status = new Status(
                                                        statusEntity.getId(),
                                                        statusEntity.getName(),
                                                        statusEntity.getDescription()
                                                );

                                                return mapper.toDomain(savedEntity, null, status);
                                            })
                                            .flatMap(updatedRequest ->
                                                    notificationPort.notifyDecision(updatedRequest.getEmail(), decision)
                                                            .thenReturn(updatedRequest)
                                            );
                                })
                )
                .doOnSuccess(req -> log.info("✅ Solicitud {} actualizada a {}", requestId, decision))
                .doOnError(error -> log.error("❌ Error actualizando solicitud {}: {}", requestId, error.getMessage()));
    }

}
