package pragma.crediya.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterUserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate dateBirth;
    private String address;
    private String phone;
    private String email;
    private Double salary;
    private Long rolId;

    public RegisterUserResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
