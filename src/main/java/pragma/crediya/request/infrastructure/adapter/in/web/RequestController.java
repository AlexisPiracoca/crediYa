package pragma.crediya.request.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pragma.crediya.request.application.dto.request.RegisterRequestDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.handler.RegisterRequestHandler;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import pragma.crediya.user.infrastructure.service.AuthorizationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solicitud")
@Tag(name = "Solicitudes", description = "API para gestión de solicitudes de préstamos")
public class RequestController {

    private final RegisterRequestHandler registerRequestHandler;
    private final AuthorizationService authorizationService;

    @PostMapping
    @Operation(
            summary = "Registrar nueva solicitud de préstamo",
            description = "Crea una nueva solicitud de préstamo validando email único y tipo de préstamo válido. Los clientes solo pueden crear solicitudes para su propio email.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Solicitud registrada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterRequestResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Ejemplo exitoso",
                                            value = """
                                                    {
                                                      "id": "12345",
                                                      "mensaje": "Solicitud registrada exitosamente",
                                                      "estado": "PENDIENTE"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos, email duplicado o tipo de préstamo no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            value = """
                                                    {
                                                      "codigo": "400",
                                                      "mensaje": "El email ya está registrado",
                                                      "detalle": "user@test.com ya existe en el sistema"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No tiene permisos para crear solicitudes para este email",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Sin permisos para email",
                                            value = """
                                                    {
                                                      "codigo": "403",
                                                      "mensaje": "Los clientes solo pueden crear solicitudes para su propio email"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            value = """
                                                    {
                                                      "codigo": "500",
                                                      "mensaje": "Error interno del servidor"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public Mono<ResponseEntity<RegisterRequestResponseDto>> registerRequest(
            @Valid @RequestBody RegisterRequestDto requestDto) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> {
                    if (!authorizationService.canCreateRequest(auth)) {
                        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new RegisterRequestResponseDto(null, "No tiene permisos para crear solicitudes")));
                    }

                    if (!authorizationService.canCreateRequestForEmail(auth, requestDto.getEmail())) {
                        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new RegisterRequestResponseDto(null, "Email no válido para crear la solicitud")));
                    }

                    return registerRequestHandler.handle(requestDto)
                            .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp));
                });
    }

    @GetMapping
    @Operation(
            summary = "Listar solicitudes",
            description = "Devuelve las solicitudes según el rol del usuario: Administrador y Asesor ven todas, Cliente solo ve las suyas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de solicitudes obtenido correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterRequestResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Ejemplo lista",
                                            value = """
                                                    [
                                                      {
                                                        "id": "12345",
                                                        "amount": 5000000,
                                                        "term": 12,
                                                        "email": "juan.perez@test.com",
                                                        "status": {
                                                          "id": 1,
                                                          "name": "PENDIENTE",
                                                          "description": "Solicitud en espera"
                                                        }
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado: el usuario no tiene permisos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Acceso denegado",
                                            value = """
                                                    {
                                                      "codigo": "403",
                                                      "mensaje": "No tiene permisos para acceder a este recurso"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            value = """
                                                    {
                                                      "codigo": "500",
                                                      "mensaje": "Error interno del servidor"
                                                    }
                                                    """
                                    )
                            ))
                            }
                    )
    public Mono<ResponseEntity<Flux<RegisterRequestResponseDto>>> getAllRequests() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> {
                    Flux<RegisterRequestResponseDto> requests;

                    if (authorizationService.canListAllRequests(auth)) {
                        requests = registerRequestHandler.getAllRequestsForAdvisor();
                    } else {
                        requests = registerRequestHandler.getRequestsByEmail(auth.getName());
                    }

                    return Mono.just(ResponseEntity.ok(requests));
                });
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener solicitud por ID",
            description = "Devuelve la información detallada de una solicitud. Los clientes solo pueden ver sus propias solicitudes.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Solicitud encontrada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterRequestResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Ejemplo exitoso",
                                            value = """
                                                {
                                                  "id": 5,
                                                  "amount": 2000000,
                                                  "term": 12,
                                                  "email": "ana.gomez@test.com",
                                                  "status": {
                                                    "id": 1,
                                                    "name": "PENDIENTE",
                                                    "description": "Solicitud en espera de aprobación"
                                                  },
                                                  "loanType": {
                                                    "id": 2,
                                                    "name": "PERSONAL",
                                                    "minAmount": 1000000,
                                                    "maxAmount": 5000000,
                                                    "interestRate": 0.15,
                                                    "autoValidation": true
                                                  }
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No tiene permisos para ver esta solicitud",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Sin permisos",
                                            value = """
                                                {
                                                  "codigo": "403",
                                                  "mensaje": "No tiene permisos para ver esta solicitud"
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontró la solicitud con el ID proporcionado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "Solicitud no encontrada",
                                            value = """
                                                {
                                                  "codigo": "404",
                                                  "mensaje": "Solicitud no encontrada",
                                                  "detalle": "No existe una solicitud con el id 5"
                                                }
                                                """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            value = """
                                                {
                                                  "codigo": "500",
                                                  "mensaje": "Error interno del servidor"
                                                }
                                                """
                                    )
                            )
                    )
            }
    )
    public Mono<ResponseEntity<RegisterRequestResponseDto>> getRequestById(@PathVariable Long id) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication())
                .flatMap(auth -> registerRequestHandler.getRequestById(id)
                        .flatMap(requestResp -> {
                            if (!authorizationService.canViewRequest(auth, requestResp.getEmail())) {
                                return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(new RegisterRequestResponseDto(null, "No tiene permisos para ver esta solicitud")));
                            }
                            return Mono.just(ResponseEntity.ok(requestResp));
                        })
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                );
    }
}