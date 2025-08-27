package pragma.crediya.request.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @NotNull(message = "El monto no debe estar vacío")
    private Double amount;

    @NotNull(message = "El plazo no debe estar vacío")
    private Integer term;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotNull(message = "El tipo de préstamo es obligatorio")
    private Long loanTypeId;
}