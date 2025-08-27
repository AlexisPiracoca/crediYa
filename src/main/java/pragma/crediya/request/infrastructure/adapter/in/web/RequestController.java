package pragma.crediya.request.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pragma.crediya.request.application.dto.request.RegisterRequestDto;
import pragma.crediya.request.application.dto.response.RegisterRequestResponseDto;
import pragma.crediya.request.application.handler.RegisterRequestHandler;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solicitud")
public class RequestController {

    private final RegisterRequestHandler registerRequestHandler;

    @PostMapping
    public Mono<ResponseEntity<RegisterRequestResponseDto>> registerUser(@RequestBody @Valid RegisterRequestDto requestDto) {
        return registerRequestHandler.handle(requestDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }
}
