package pragma.crediya.user.application.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pragma.crediya.user.application.dto.request.LoginRequestDto;
import pragma.crediya.user.application.dto.response.LoginResponseDto;
import pragma.crediya.user.application.mapper.AuthDtoMapper;
import pragma.crediya.user.application.useCase.AuthenticateUserUseCase;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationHandler {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthDtoMapper authDtoMapper;

    public AuthenticationHandler(AuthenticateUserUseCase authenticateUserUseCase,
                                 AuthDtoMapper authDtoMapper) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.authDtoMapper = authDtoMapper;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequestDto.class)
                .map(authDtoMapper::toDomain)
                .flatMap(authenticateUserUseCase::execute)
                .map(authDtoMapper::toResponseDto)
                .flatMap(response -> {
                    if (response.isSuccess()) {
                        return ServerResponse.ok().bodyValue(response);
                    } else {
                        return ServerResponse.status(401).bodyValue(response);
                    }
                })
                .onErrorResume(error ->
                        ServerResponse.status(500)
                                .bodyValue(new LoginResponseDto(null,
                                        "Error interno del servidor", false, null))
                );
    }
}
