package pragma.crediya.request.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.infrastructure.entity.RequestEntitiy;

@Component
public class RequestMapper {

    public RequestEntitiy toEntity(Request request) {
        return new RequestEntitiy(
                request.getId(),
                request.getAmount(),
                request.getTerm(),
                request.getEmail(),
                request.getStatus().getId(),
                request.getLoanType().getId()
        );
    }

    public Request toDomain(RequestEntitiy entity, LoanType loanType, Status status) {
        return new Request(
                entity.getId(),
                entity.getAmount(),
                entity.getTerm(),
                entity.getEmail(),
                status,
                loanType
        );
    }
}
