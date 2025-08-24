package pragma.crediya.request.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntitiy {

    @Id
    @Column("id_loan_request")
    private Long id;

    @Column("amount")
    private Double amount;

    @Column("term")
    private Integer term;

    @Column("email")
    private String email;

    @Column("id_status")
    private Long statusId;

    @Column("id_loan_type")
    private Long loanTypeId;
}
