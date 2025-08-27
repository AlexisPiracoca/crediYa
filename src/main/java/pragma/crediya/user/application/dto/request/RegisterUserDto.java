package pragma.crediya.user.application.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para registrar un nuevo usuario")
public class RegisterUserDto {

    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @Schema(description = "Fecha de nacimiento", example = "1990-05-15", type = "string", format = "date")
    private LocalDate dateBirth;

    @Schema(description = "Dirección de residencia", example = "Calle 123 # 45-67")
    private String address;

    @Schema(description = "Número de teléfono", example = "3001234567")
    private String phone;

    @Schema(description = "Correo electrónico (único)", example = "juan.perez@email.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Schema(description = "Salario mensual", example = "2500000", minimum = "0", maximum = "15000000", required = true)
    @NotNull(message = "El salario base es obligatorio")
    @DecimalMin(value = "0", message = "El salario debe ser mínimo 0")
    @DecimalMax(value = "15000000", message = "El salario no puede ser mayor a 15,000,000")
    private Double salary;

}

