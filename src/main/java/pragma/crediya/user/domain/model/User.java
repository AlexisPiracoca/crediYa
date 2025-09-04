package pragma.crediya.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String lastName;
    private LocalDate dateBirth;
    private String address;
    private String phone;
    private String email;
    private Double salary;
    private String password;
    private Rol rol;

}
