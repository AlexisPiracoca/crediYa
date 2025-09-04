package pragma.crediya.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para respuestas de error")
public class ErrorResponseDto {

    @Schema(description = "Código de error HTTP", example = "400")
    private String codigo;

    @Schema(description = "Mensaje descriptivo del error", example = "Datos inválidos")
    private String mensaje;

    @Schema(description = "Detalle adicional del error", example = "El campo email es requerido")
    private String detalle;

    public ErrorResponseDto(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
}