package pragma.crediya.user.infrastructure.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDate;

@Table("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("lastname")
    private String lastName;

    @Column("datebirth")
    private LocalDate dateBirth;

    @Column("address")
    private String address;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("salary")
    private Double salary;
}
