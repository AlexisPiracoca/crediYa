package pragma.crediya.request.application.handler;

import lombok.*;
import org.springframework.stereotype.Component;
import pragma.crediya.request.application.dto.request.RegisterRequestDto;
import pragma.crediya.request.application.dto.request.RequestDtoMapper;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.useCase.RegisterRequestUseCase;
import pragma.crediya.request.domain.model.Request;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterRequestHandler {

    private final RegisterRequestUseCase registerRequestUseCase;
    private final RequestDtoMapper dtoMapper;

    public Mono<RegisterRequestResponseDto> handle(RegisterRequestDto requestDto) {
        Request domainRequest = dtoMapper.toDomain(requestDto);

        return registerRequestUseCase.registerRequest(domainRequest)
                .map(dtoMapper::toResponseDto);
    }
}
