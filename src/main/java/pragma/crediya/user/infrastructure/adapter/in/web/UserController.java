package pragma.crediya.user.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.handler.UserHandler;
import pragma.crediya.user.infrastructure.service.AuthorizationService;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UserController {

    private final UserHandler userHandler;
    private final AuthorizationService authorizationService;

    @PostMapping
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en el sistema. Solo usuarios con rol Administrador o Asesor pueden crear usuarios.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario registrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterUserResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No tiene permisos para crear usuarios",
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

    public Mono<ResponseEntity<Object>> registerUser(@RequestBody @Valid RegisterUserDto userDto,
                                                     Authentication authentication) {

        if (!authorizationService.canCreateUser(authentication)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensaje", "No tiene permisos para crear usuarios")));
        }

        return userHandler.handleRegisterUser(userDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body((Object) response))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("mensaje", "Error al crear usuario", "detalle", ex.getMessage()))));
    }
}
