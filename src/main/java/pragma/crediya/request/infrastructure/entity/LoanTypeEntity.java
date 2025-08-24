package pragma.crediya.request.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("loan_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanTypeEntity {

    @Id
    @Column("id_loan_type")
    private Long id;

    @Column("name")
    private String name;

    @Column("min_amount")
    private Double minAmount;

    @Column("max_amount")
    private Double maxAmount;

    @Column("interest_rate")
    private Double interestRate;

    @Column("auto_validation")
    private Boolean autoValidation;
}