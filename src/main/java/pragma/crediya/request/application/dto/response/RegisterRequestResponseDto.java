package pragma.crediya.request.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestResponseDto {
    private Long id;
    private Double amount;
    private Integer term;
    private String email;
    private StatusDto status;
    private LoanTypeDto loanType;

    public RegisterRequestResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
