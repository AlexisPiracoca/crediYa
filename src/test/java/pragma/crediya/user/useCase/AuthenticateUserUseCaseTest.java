package pragma.crediya.user.useCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pragma.crediya.user.application.useCase.AuthenticateUserUseCase;
import pragma.crediya.user.domain.model.AuthenticationRequest;
import pragma.crediya.user.domain.model.AuthenticationResponse;
import pragma.crediya.user.domain.ports.AuthenticationRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticateUserUseCaseTest {

    private AuthenticationRepository authRepository;
    private AuthenticateUserUseCase useCase;

    @BeforeEach
    void setUp() {
        authRepository = Mockito.mock(AuthenticationRepository.class);
        useCase = new AuthenticateUserUseCase(authRepository);
    }

    @Test
    void testAuthenticationSuccess() {
        AuthenticationRequest request = new AuthenticationRequest("user@test.com", "password123");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("token123", "Login correcto", true, null);

        when(authRepository.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(Mono.just(expectedResponse));

        StepVerifier.create(useCase.execute(request))
                .expectNextMatches(response ->
                        response.isSuccess() &&
                                response.getMessage().equals("Login correcto") &&
                                response.getToken().equals("token123")
                )
                .verifyComplete();

        verify(authRepository, times(1)).authenticate(request);
    }

    @Test
    void testAuthenticationFailed_InvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("user@test.com", "wrongpass");
        AuthenticationResponse expectedResponse = new AuthenticationResponse(null, "Credenciales inválidas", false, null);

        when(authRepository.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(Mono.just(expectedResponse));

        StepVerifier.create(useCase.execute(request))
                .expectNextMatches(response ->
                        !response.isSuccess() &&
                                response.getMessage().equals("Credenciales inválidas") &&
                                response.getToken() == null
                )
                .verifyComplete();

        verify(authRepository, times(1)).authenticate(request);
    }

    @Test
    void testAuthenticationError() {
        AuthenticationRequest request = new AuthenticationRequest("user@test.com", "password123");

        when(authRepository.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("DB not available")));

        StepVerifier.create(useCase.execute(request))
                .expectNextMatches(response ->
                        !response.isSuccess() &&
                                response.getMessage().equals("Error en el sistema de autenticación") &&
                                response.getToken() == null
                )
                .verifyComplete();

        verify(authRepository, times(1)).authenticate(request);
    }
}

