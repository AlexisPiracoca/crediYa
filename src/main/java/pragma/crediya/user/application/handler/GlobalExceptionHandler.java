package pragma.crediya.user.application.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pragma.crediya.user.application.dto.response.ErrorResponseDto;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleValidationException(WebExchangeBindException ex, ServerHttpRequest request) {
        String message = ex.getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDto error = new ErrorResponseDto(
                400,
                message,
                LocalDateTime.now(),
                request.getPath().value()
        );
        return Mono.just(ResponseEntity.badRequest().body(error));
    }


    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleRuntimeException(RuntimeException ex) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of("error", ex.getMessage())));
    }
}


