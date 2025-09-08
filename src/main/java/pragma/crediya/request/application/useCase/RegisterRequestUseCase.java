package pragma.crediya.request.application.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.domain.ports.LoanTypeRepository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.infrastructure.entity.RequestEntity;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import reactor.core.publisher.Mono;
import pragma.crediya.request.domain.model.Request;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterRequestUseCase {

    private final RequestRepository requestRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StatusRepository statusRepository;
    private final RequestMapper mapper;

    public Mono<Request> registerRequest(Request request) {
        log.info("Iniciando registro de solicitud para email: {}", request.getEmail());

        return requestRepository.existsByEmail(request.getEmail())
                .doOnNext(emailExists -> log.debug("¿Existe solicitud previa para {}? {}", request.getEmail(), emailExists))
                .flatMap(emailExists -> {
                    if (emailExists) {
                        log.warn("Solicitud rechazada: el correo {} ya tiene una solicitud pendiente.", request.getEmail());
                        return Mono.error(new RuntimeException("El correo electrónico ya cuenta con una solicitud pendiente"));
                    }

                    log.info("Validando tipo de préstamo con ID: {}", request.getLoanType().getId());
                    return loanTypeRepository.findById(request.getLoanType().getId())
                            .switchIfEmpty(Mono.error(new RuntimeException("Tipo de préstamo no encontrado")))
                            .flatMap(loanTypeEntity -> {
                                log.info("Tipo de préstamo encontrado: {}", loanTypeEntity.getName());

                                log.info("Asignando estado inicial PENDIENTE (ID=1) a la solicitud");
                                return statusRepository.findById(1L)
                                        .switchIfEmpty(Mono.error(new RuntimeException("Estado Pendiente no encontrado")))
                                        .flatMap(statusEntity -> {
                                            log.debug("Estado encontrado: {}", statusEntity.getName());

                                            LoanType loanType = new LoanType(
                                                    loanTypeEntity.getId(),
                                                    loanTypeEntity.getName(),
                                                    loanTypeEntity.getMinAmount(),
                                                    loanTypeEntity.getMaxAmount(),
                                                    loanTypeEntity.getInterestRate(),
                                                    loanTypeEntity.getAutoValidation()
                                            );

                                            Status status = new Status(
                                                    statusEntity.getId(),
                                                    statusEntity.getName(),
                                                    statusEntity.getDescription()
                                            );

                                            request.setLoanType(loanType);
                                            request.setStatus(status);

                                            log.info("Guardando solicitud para email: {}", request.getEmail());
                                            RequestEntity requestEntity = mapper.toEntity(request);

                                            return requestRepository.save(requestEntity)
                                                    .doOnSuccess(saved -> log.info("Solicitud guardada con ID: {}", saved.getId()))
                                                    .map(savedEntity -> mapper.toDomain(savedEntity, loanType, status));
                                        });
                            });
                })
                .doOnError(error -> log.error("Error al registrar solicitud para {}: {}", request.getEmail(), error.getMessage()));
    }
}
