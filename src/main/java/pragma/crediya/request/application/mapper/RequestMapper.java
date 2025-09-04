package pragma.crediya.request.application.mapper;

import pragma.crediya.request.domain.model.LoanType;
import pragma.crediya.request.domain.model.Request;
import pragma.crediya.request.domain.model.Status;
import pragma.crediya.request.infrastructure.entity.RequestEntity;

public interface RequestMapper {
    Request toDomain(RequestEntity entity, LoanType loanType, Status status);
}
