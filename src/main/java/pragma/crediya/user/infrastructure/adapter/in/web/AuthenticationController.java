package pragma.crediya.user.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pragma.crediya.user.application.dto.request.LoginRequestDto;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import pragma.crediya.user.application.dto.response.LoginResponseDto;
import pragma.crediya.user.application.handler.AuthenticationHandler;

@Configuration
@Tag(name = "Autenticación", description = "API para autenticación de usuarios")
public class AuthenticationController {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/login",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = AuthenticationHandler.class,
                    beanMethod = "login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Autenticar usuario en el sistema",
                            description = "Recibe las credenciales de acceso (email y contraseña) y retorna un token JWT si son válidas.",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Credenciales de acceso del usuario",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = LoginRequestDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Usuario autenticado correctamente",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = LoginResponseDto.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Credenciales inválidas o datos incompletos",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = ErrorResponseDto.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Error interno del servidor"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> authRoutes(AuthenticationHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/v1/login")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        handler::login);
    }
}