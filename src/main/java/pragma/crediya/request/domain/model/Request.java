package pragma.crediya.request.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private Long id;
    private Double amount;
    private Integer term;
    private String email;
    private Status status;
    private LoanType loanType;

}
