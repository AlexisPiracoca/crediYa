package pragma.crediya.request.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pragma.crediya.request.application.dto.request.RegisterRequestDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.handler.RegisterRequestHandler;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solicitud")
@Tag(name = "Solicitudes", description = "API para gestión de solicitudes de préstamos")
public class RequestController {

    private final RegisterRequestHandler registerRequestHandler;

    @PostMapping
    @Operation(
            summary = "Registrar nueva solicitud de préstamo",
            description = "Crea una nueva solicitud de préstamo validando email único y tipo de préstamo válido",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Solicitud registrada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterRequestResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos, email duplicado o tipo de préstamo no encontrado",
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
    public Mono<ResponseEntity<RegisterRequestResponseDto>> registerRequest(
            @Parameter(description = "Datos de la solicitud de préstamo", required = true)
            @RequestBody @Valid RegisterRequestDto requestDto) {

        return registerRequestHandler.handle(requestDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }
}
