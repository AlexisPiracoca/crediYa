package pragma.crediya.request.application.dto.request;

import org.springframework.stereotype.Component;
import pragma.crediya.request.application.dto.response.LoanTypeDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.dto.response.StatusDto;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;

@Component
public class RequestDtoMapper {

    public Request toDomain(RegisterRequestDto dto) {
        Request request = new Request();
        request.setAmount(dto.getAmount());
        request.setTerm(dto.getTerm());
        request.setEmail(dto.getEmail());

        LoanType loanType = new LoanType();
        loanType.setId(dto.getLoanTypeId());
        request.setLoanType(loanType);

        return request;
    }

    public RegisterRequestResponseDto toResponseDto(Request domain) {
        return new RegisterRequestResponseDto(
                domain.getId(),
                domain.getAmount(),
                domain.getTerm(),
                domain.getEmail(),
                toStatusDto(domain.getStatus()),
                toLoanTypeDto(domain.getLoanType())
        );
    }

    private StatusDto toStatusDto(Status status) {
        return new StatusDto(
                status.getId(),
                status.getName(),
                status.getDescription()
        );
    }

    private LoanTypeDto toLoanTypeDto(LoanType loanType) {
        return new LoanTypeDto(
                loanType.getId(),
                loanType.getName(),
                loanType.getMinAmount(),
                loanType.getMaxAmount(),
                loanType.getInterestRate(),
                loanType.getAutoValidation()
        );
    }
}
