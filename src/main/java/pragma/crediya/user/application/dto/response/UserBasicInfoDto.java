package pragma.crediya.user.application.dto.response;

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
public class UserBasicInfoDto {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
