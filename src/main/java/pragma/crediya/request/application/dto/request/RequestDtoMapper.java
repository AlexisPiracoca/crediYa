package pragma.crediya.request.application.dto.request;

import org.springframework.stereotype.Component;
import pragma.crediya.request.application.dto.response.LoanTypeDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.dto.response.StatusDto;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.infrastructure.entity.LoanTypeEntity;
import pragma.crediya.request.infrastructure.entity.StatusEntity;

@Component
public class RequestDtoMapper {

    // ---- Domain mappers ----
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

    // ---- Domain to DTO ----
    public StatusDto toStatusDto(Status status) {
        return new StatusDto(
                status.getId(),
                status.getName(),
                status.getDescription()
        );
    }

    public LoanTypeDto toLoanTypeDto(LoanType loanType) {
        return new LoanTypeDto(
                loanType.getId(),
                loanType.getName(),
                loanType.getMinAmount(),
                loanType.getMaxAmount(),
                loanType.getInterestRate(),
                loanType.getAutoValidation()
        );
    }

    // ---- Entity to DTO ----
    public StatusDto toStatusDto(StatusEntity entity) {
        return new StatusDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public LoanTypeDto toLoanTypeDto(LoanTypeEntity entity) {
        return new LoanTypeDto(
                entity.getId(),
                entity.getName(),
                entity.getMinAmount(),
                entity.getMaxAmount(),
                entity.getInterestRate(),
                entity.getAutoValidation()
        );
    }
}
