package pragma.crediya.user.application.dto.response;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private LocalDate dateBirth;
    private String address;
    private String phone;
    private String email;
    private Double salary;
}
