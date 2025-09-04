package pragma.crediya.user.application.useCase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pragma.crediya.user.domain.model.AuthenticationRequest;
import pragma.crediya.user.domain.model.AuthenticationResponse;
import pragma.crediya.user.domain.ports.AuthenticationRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AuthenticateUserUseCase {
    private final AuthenticationRepository authRepository;

    public AuthenticateUserUseCase(AuthenticationRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Mono<AuthenticationResponse> execute(AuthenticationRequest request) {
        return authRepository.authenticate(request)
                .doOnSuccess(response -> {
                    if (response.isSuccess()) {
                        log.info("LOGIN_SUCCESS - Usuario {} autenticado correctamente", request.getEmail());
                    } else {
                        log.warn("LOGIN_FAILED - Credenciales inválidas para usuario {}", request.getEmail());
                    }
                })
                .doOnError(error -> log.error("LOGIN_ERROR - Error autenticando usuario {}: ", request.getEmail(), error))
                .onErrorResume(error -> Mono.just(new AuthenticationResponse(
                        null,
                        "Error en el sistema de autenticación",
                        false,
                        null
                )));
    }
}
