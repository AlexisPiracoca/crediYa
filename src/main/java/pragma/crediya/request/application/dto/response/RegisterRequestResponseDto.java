package pragma.crediya.request.application.dto.response;

import lombok.*;

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
}
