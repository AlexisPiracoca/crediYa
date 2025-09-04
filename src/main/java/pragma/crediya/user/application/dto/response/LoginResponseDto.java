package pragma.crediya.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "LoginResponseDto", description = "Respuesta del sistema de autenticación")
public class LoginResponseDto {
    @Schema(description = "Token JWT de sesión", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    private String token;

    @Schema(description = "Mensaje descriptivo de la operación", example = "Autenticación correcta")
    private String message;

    @Schema(description = "Indica si la autenticación fue exitosa", example = "true")
    private boolean success;

    @Schema(description = "Información básica del usuario autenticado")
    private UserBasicInfoDto user;
}
