package pragma.crediya.user.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    private LocalDate dateBirth;
    private String address;
    private String phone;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotNull(message = "El salario base es obligatorio")
    @Min(value = 0, message = "El salario debe ser entre 0 y 15,000,000")
    @Max(value = 15000000, message = "El salario no puede ser mayor a 15,000,000")
    private Double salary;
}
