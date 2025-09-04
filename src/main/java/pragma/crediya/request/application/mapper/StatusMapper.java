package pragma.crediya.request.application.mapper;

import pragma.crediya.request.application.dto.response.StatusDto;
import pragma.crediya.request.domain.model.Status;

public class StatusMapper {

    public static Status toDomain(StatusDto dto) {
        return new Status(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }
}

