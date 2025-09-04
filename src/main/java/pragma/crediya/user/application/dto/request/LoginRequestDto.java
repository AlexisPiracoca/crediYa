package pragma.crediya.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Schema(name = "LoginRequestDto", description = "Credenciales de acceso del usuario")
public class LoginRequestDto {
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email es inválido")
    @Schema(description = "Email del usuario", example = "alex@example.com")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Schema(description = "Contraseña del usuario", example = "12345678")
    private String password;
}
