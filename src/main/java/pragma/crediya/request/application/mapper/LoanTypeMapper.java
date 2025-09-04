package pragma.crediya.request.application.mapper;

import pragma.crediya.request.application.dto.response.LoanTypeDto;
import pragma.crediya.request.domain.model.LoanType;

public class LoanTypeMapper {

    public static LoanType toDomain(LoanTypeDto dto) {
        return new LoanType(
                dto.getId(),
                dto.getName(),
                dto.getInterestRate(),
                dto.getMaxAmount(),
                dto.getMinAmount(),
                dto.getAutoValidation()
        );
    }
}

