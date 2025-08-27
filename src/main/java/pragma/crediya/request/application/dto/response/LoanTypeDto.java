package pragma.crediya.request.application.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanTypeDto {
    private Long id;
    private String name;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
