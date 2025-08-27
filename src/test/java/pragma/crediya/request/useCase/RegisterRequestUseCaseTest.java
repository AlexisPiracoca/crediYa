package pragma.crediya.request.useCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pragma.crediya.request.application.useCase.RegisterRequestUseCase;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.domain.ports.LoanTypeRepository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.domain.ports.StatusRepository;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;
import pragma.crediya.request.infrastructure.entity.RequestEntity;
import pragma.crediya.request.infrastructure.entity.StatusEntity;
import pragma.crediya.request.infrastructure.mapper.RequestMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterRequestUseCaseTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private LoanTypeRepository loanTypeRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private RegisterRequestUseCase registerRequestUseCase;

    private Request testRequest;
    private LoanTypeEntity loanTypeEntity;
    private StatusEntity statusEntity;
    private RequestEntity requestEntity;
    private RequestEntity savedRequestEntity;

    @BeforeEach
    void setUp() {
        LoanType loanType = new LoanType();
        loanType.setId(1L);

        testRequest = new Request();
        testRequest.setEmail("test@example.com");
        testRequest.setAmount(50000.0);
        testRequest.setTerm(12);
        testRequest.setLoanType(loanType);

        loanTypeEntity = new LoanTypeEntity();
        loanTypeEntity.setId(1L);
        loanTypeEntity.setName("Préstamo Personal");
        loanTypeEntity.setMinAmount(10000.0);
        loanTypeEntity.setMaxAmount(100000.0);
        loanTypeEntity.setInterestRate(15.5);
        loanTypeEntity.setAutoValidation(false);

        statusEntity = new StatusEntity();
        statusEntity.setId(1L);
        statusEntity.setName("Pendiente");
        statusEntity.setDescription("Solicitud pendiente de revisión");

        requestEntity = new RequestEntity();
        requestEntity.setEmail("test@example.com");
        requestEntity.setAmount(50000.0);
        requestEntity.setTerm(12);
        requestEntity.setLoanTypeId(1L);
        requestEntity.setStatusId(1L);

        savedRequestEntity = new RequestEntity();
        savedRequestEntity.setId(1L);
        savedRequestEntity.setEmail("test@example.com");
        savedRequestEntity.setAmount(50000.0);
        savedRequestEntity.setTerm(12);
        savedRequestEntity.setLoanTypeId(1L);
        savedRequestEntity.setStatusId(1L);
    }

    @Test
    @DisplayName("Debe registrar solicitud exitosamente cuando el email no existe")
    void shouldRegisterRequestSuccessfully_WhenEmailDoesNotExist() {
        LoanType expectedLoanType = new LoanType(1L, "Préstamo Personal", 10000.0, 100000.0, 15.5, false);
        Status expectedStatus = new Status(1L, "Pendiente", "Solicitud pendiente de revisión");
        Request expectedRequest = new Request(1L, 50000.0, 12, "test@example.com", expectedStatus, expectedLoanType);

        when(requestRepository.existsByEmail("test@example.com")).thenReturn(Mono.just(false));
        when(loanTypeRepository.findById(1L)).thenReturn(Mono.just(loanTypeEntity));
        when(statusRepository.findById(1L)).thenReturn(Mono.just(statusEntity));
        when(requestMapper.toEntity(any(Request.class))).thenReturn(requestEntity);
        when(requestRepository.save(requestEntity)).thenReturn(Mono.just(savedRequestEntity));
        when(requestMapper.toDomain(eq(savedRequestEntity), any(LoanType.class), any(Status.class)))
                .thenReturn(expectedRequest);

        Mono<Request> result = registerRequestUseCase.registerRequest(testRequest);

        StepVerifier.create(result)
                .expectNext(expectedRequest)
                .verifyComplete();

        verify(requestRepository).existsByEmail("test@example.com");
        verify(loanTypeRepository).findById(1L);
        verify(statusRepository).findById(1L);
        verify(requestMapper).toEntity(any(Request.class));
        verify(requestRepository).save(requestEntity);
        verify(requestMapper).toDomain(eq(savedRequestEntity), any(LoanType.class), any(Status.class));
    }

    @Test
    @DisplayName("Debe fallar cuando el email ya existe")
    void shouldFailToRegisterRequest_WhenEmailAlreadyExists() {
        when(requestRepository.existsByEmail("test@example.com")).thenReturn(Mono.just(true));

        Mono<Request> result = registerRequestUseCase.registerRequest(testRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("El correo electrónico ya cuenta con una solicitud pendiente"))
                .verify();

        verify(requestRepository).existsByEmail("test@example.com");
        verify(loanTypeRepository, never()).findById(anyLong());
        verify(statusRepository, never()).findById(anyLong());
        verify(requestRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe fallar cuando el tipo de préstamo no existe")
    void shouldFailToRegisterRequest_WhenLoanTypeNotFound() {
        when(requestRepository.existsByEmail("test@example.com")).thenReturn(Mono.just(false));
        when(loanTypeRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Request> result = registerRequestUseCase.registerRequest(testRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Tipo de préstamo no encontrado"))
                .verify();

        verify(requestRepository).existsByEmail("test@example.com");
        verify(loanTypeRepository).findById(1L);
        verify(statusRepository, never()).findById(anyLong());
        verify(requestRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe fallar cuando el estado no se encuentra")
    void shouldFailToRegisterRequest_WhenStatusNotFound() {
        when(requestRepository.existsByEmail("test@example.com")).thenReturn(Mono.just(false));
        when(loanTypeRepository.findById(1L)).thenReturn(Mono.just(loanTypeEntity));
        when(statusRepository.findById(1L)).thenReturn(Mono.empty());

        Mono<Request> result = registerRequestUseCase.registerRequest(testRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Estado Pendiente no encontrado"))
                .verify();

        verify(requestRepository).existsByEmail("test@example.com");
        verify(loanTypeRepository).findById(1L);
        verify(statusRepository).findById(1L);
        verify(requestRepository, never()).save(any());
    }
}

