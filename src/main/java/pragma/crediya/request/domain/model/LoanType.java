package pragma.crediya.request.domain.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanType {

    private Long id;
    private String name;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
