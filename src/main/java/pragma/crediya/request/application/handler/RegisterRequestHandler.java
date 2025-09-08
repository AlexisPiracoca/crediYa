package pragma.crediya.request.application.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pragma.crediya.request.application.dto.request.RegisterRequestDto;
import pragma.crediya.request.application.dto.request.RequestDtoMapper;
import pragma.crediya.request.application.dto.response.LoanTypeDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.dto.response.StatusDto;
import pragma.crediya.request.application.useCase.ApproveRejectRequestUseCase;
import pragma.crediya.request.application.useCase.GetRequestsByEmailUseCase;
import pragma.crediya.request.application.useCase.RegisterRequestUseCase;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import pragma.crediya.request.infrastructure.repository.LoanTypeJpaRepository;
import pragma.crediya.request.infrastructure.repository.RequestJpaRepository;
import pragma.crediya.request.infrastructure.repository.StatusJpaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterRequestHandler {

    private final RegisterRequestUseCase registerRequestUseCase;
    private final RequestDtoMapper dtoMapper;

    private final RequestJpaRepository requestRepository;
    private final StatusJpaRepository statusRepository;
    private final LoanTypeJpaRepository loanTypeRepository;
    private final GetRequestsByEmailUseCase getRequestsByEmailUseCase;
    private final RequestMapper requestMapper;
    private final ApproveRejectRequestUseCase approveRejectRequestUseCase;

    // POST
    public Mono<RegisterRequestResponseDto> handle(RegisterRequestDto requestDto) {
        Request domainRequest = dtoMapper.toDomain(requestDto);

        return registerRequestUseCase.registerRequest(domainRequest)
                .map(dtoMapper::toResponseDto);
    }

    // GET ALL con paginación manual
    public Flux<RegisterRequestResponseDto> getAllRequestsForAdvisor() {
        int page = 0; // podrías recibir estos valores como parámetros
        int size = 10;

        return requestRepository.findAll()
                .skip((long) page * size)
                .take(size)
                .flatMap(request ->
                        Mono.zip(
                                statusRepository.findById(request.getStatusId()),
                                loanTypeRepository.findById(request.getLoanTypeId())
                        ).map(tuple -> new RegisterRequestResponseDto(
                                request.getId(),
                                request.getAmount(),
                                request.getTerm(),
                                request.getEmail(),
                                dtoMapper.toStatusDto(tuple.getT1()),
                                dtoMapper.toLoanTypeDto(tuple.getT2())
                        ))
                );
    }

    // GET by ID
    public Mono<RegisterRequestResponseDto> getRequestById(Long id) {
        return requestRepository.findById(id)
                .flatMap(request ->
                        Mono.zip(
                                statusRepository.findById(request.getStatusId()),
                                loanTypeRepository.findById(request.getLoanTypeId())
                        ).map(tuple -> new RegisterRequestResponseDto(
                                request.getId(),
                                request.getAmount(),
                                request.getTerm(),
                                request.getEmail(),
                                dtoMapper.toStatusDto(tuple.getT1()),
                                dtoMapper.toLoanTypeDto(tuple.getT2())
                        ))
                );
    }

    public Flux<RegisterRequestResponseDto> getRequestsByEmail(String email) {
        return getRequestsByEmailUseCase.execute(email)
                .map(dtoMapper::toResponseDto);
    }

    public Mono<RegisterRequestResponseDto> updateDecision(Long id, Long statusId) {
        return statusRepository.findById(statusId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El estado con id " + statusId + " no existe")))
                .flatMap(status ->
                        requestRepository.findById(id)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("La solicitud con id " + id + " no existe")))
                                .flatMap(request -> {
                                    request.setStatusId(statusId);
                                    return requestRepository.save(request);
                                })
                                .map(saved -> new RegisterRequestResponseDto(
                                        saved.getId(),
                                        saved.getAmount(),
                                        saved.getTerm(),
                                        saved.getEmail(),
                                        new StatusDto(status.getId(), status.getName(), status.getDescription()),
                                        new LoanTypeDto(saved.getLoanTypeId(), null, null, null, null, null)
                                ))
                );
    }
}
