package pragma.crediya.user.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.dto.response.UserListResponseDto;
import pragma.crediya.user.application.handler.UserHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UserController {

    private final UserHandler userHandler;

    @PostMapping
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en el sistema validando que el email no esté duplicado",
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
                            responseCode = "400",
                            description = "Datos inválidos o email duplicado",
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
    public Mono<ResponseEntity<RegisterUserResponseDto>> registerUser(
            @Parameter(description = "Datos del usuario a registrar", required = true)
            @RequestBody @Valid RegisterUserDto userDto) {

        return userHandler.handleRegisterUser(userDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Obtiene la lista completa de usuarios registrados en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuarios obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserListResponseDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    public Flux<UserListResponseDto> listUsers() {
        return userHandler.handleListAllUsers();
    }
}