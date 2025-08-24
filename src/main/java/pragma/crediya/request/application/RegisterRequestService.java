package pragma.crediya.request.application;

import org.springframework.stereotype.Service;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.domain.ports.LoanTypeRepository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.infrastructure.entity.RequestEntitiy;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import reactor.core.publisher.Mono;
import pragma.crediya.request.domain.model.Request;


@Service
public class RegisterRequestService {

    private final RequestRepository requestRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StatusRepository statusRepository;
    private final RequestMapper mapper;

    public RegisterRequestService(
            RequestRepository requestRepository,
            LoanTypeRepository loanTypeRepository,
            StatusRepository statusRepository,
            RequestMapper mapper
    ) {
        this.requestRepository = requestRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.statusRepository = statusRepository;
        this.mapper = mapper;
    }

    public Mono<Request> registerRequest(Request request) {
        return requestRepository.existsByEmail(request.getEmail())
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(new RuntimeException("El correo electrónico ya está registrado"));
                    }
                    return loanTypeRepository.findById(request.getLoanType().getId())
                            .switchIfEmpty(Mono.error(new RuntimeException("Tipo de préstamo no encontrado")))
                            .flatMap(loanTypeEntity ->
                                    statusRepository.findById(1L)
                                            .switchIfEmpty(Mono.error(new RuntimeException("Estado Pendiente no encontrado")))
                                            .flatMap(statusEntity -> {
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

                                                RequestEntitiy requestEntity = mapper.toEntity(request);

                                                return requestRepository.save(requestEntity)
                                                        .map(savedEntity -> mapper.toDomain(savedEntity, loanType, status));
                                            })
                            );
                });
    }
}
