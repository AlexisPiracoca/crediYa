package pragma.crediya.user.application.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Contraseña del usuario", example = "P@ssw0rd!", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Schema(description = "ID del rol asignado al usuario", example = "1", required = true)
    @NotNull(message = "El ID del rol es obligatorio")
    private Long rolId;
}

