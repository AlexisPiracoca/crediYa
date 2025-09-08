package pragma.crediya.request.application.useCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pragma.crediya.request.application.dto.response.LoanTypeDto;
import pragma.crediya.request.application.dto.response.StatusDto;
import pragma.crediya.request.application.mapper.LoanTypeMapper;
import pragma.crediya.request.application.mapper.StatusMapper;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.domain.ports.LoanTypeRepository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetRequestsByEmailUseCase {

    private final RequestRepository requestRepository;
    private final RequestMapper mapper;
    private final LoanTypeRepository loanTypeRepository;
    private final StatusRepository statusRepository;

    public Flux<Request> execute(String email) {
        log.info("Consultando solicitudes para el email: {}", email);

        return requestRepository.findByEmail(email)
                .doOnNext(request -> log.debug("Solicitud encontrada: {}", request.getId()))
                .flatMap(entity -> Mono.zip(
                        loadLoanType(entity.getLoanTypeId()),
                        loadStatus(entity.getStatusId())
                ).map(tuple -> mapper.toDomain(entity, tuple.getT1(), tuple.getT2())))
                .doOnComplete(() -> log.info("Consulta de solicitudes completada para: {}", email));
    }

    private Mono<LoanType> loadLoanType(Long loanTypeId) {
        return loanTypeRepository.findById(loanTypeId)
                .map(entity -> new LoanTypeDto(
                        entity.getId(),
                        entity.getName(),
                        entity.getMinAmount(),
                        entity.getMaxAmount(),
                        entity.getInterestRate(),
                        entity.getAutoValidation()
                ))
                .map(LoanTypeMapper::toDomain) // 👈 conversión DTO → modelo
                .switchIfEmpty(Mono.error(new RuntimeException("LoanType no encontrado: " + loanTypeId)));
    }

    private Mono<Status> loadStatus(Long statusId) {
        return statusRepository.findById(statusId)
                .map(entity -> new StatusDto(
                        entity.getId(),
                        entity.getName(),
                        entity.getDescription()
                ))
                .map(StatusMapper::toDomain) // 👈 conversión DTO → modelo
                .switchIfEmpty(Mono.error(new RuntimeException("Status no encontrado: " + statusId)));
    }
}

