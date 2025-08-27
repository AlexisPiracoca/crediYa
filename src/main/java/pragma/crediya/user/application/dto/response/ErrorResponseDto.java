package pragma.crediya.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de error estándar")
public class ErrorResponseDto {

    @Schema(description = "Código de error", example = "400")
    private Integer code;

    @Schema(description = "Mensaje de error", example = "El correo electrónico ya está registrado")
    private String message;

    @Schema(description = "Timestamp del error", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "Ruta donde ocurrió el error", example = "/api/v1/usuarios")
    private String path;
}