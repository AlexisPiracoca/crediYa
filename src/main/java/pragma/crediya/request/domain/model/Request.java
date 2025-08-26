package pragma.crediya.request.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private Long id;
    @NotNull(message = "El monto no debe estar vacío")
    private Double amount;

    @NotNull(message = "El plazo no debe estar vacío")
    private Integer term;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;
    private Status status;
    private LoanType loanType;

}
