package pragma.crediya.request.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pragma.crediya.request.application.RegisterRequestService;
import pragma.crediya.request.domain.model.Request;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/solicitud")
public class RequestController {

    private final RegisterRequestService registerRequestService;

    @PostMapping
    public Mono<ResponseEntity<Request>> registerUser(@RequestBody @Valid Request request) {
        return registerRequestService.registerRequest(request)
                .map(savedRequest -> ResponseEntity.status(HttpStatus.CREATED).body(savedRequest));
    }
}
