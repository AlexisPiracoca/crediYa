package pragma.crediya.request.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO para registrar una nueva solicitud de préstamo")
public class RegisterRequestDto {

    @Schema(description = "Monto del préstamo solicitado", example = "5000000", required = true)
    @NotNull(message = "El monto no debe estar vacío")
    private Double amount;

    @Schema(description = "Plazo del préstamo en meses", example = "12", required = true)
    @NotNull(message = "El plazo no debe estar vacío")
    private Integer term;

    @Schema(description = "Correo electrónico del solicitante", example = "cliente@email.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Schema(description = "ID del tipo de préstamo", example = "1", required = true)
    @NotNull(message = "El tipo de préstamo es obligatorio")
    private Long loanTypeId;
}