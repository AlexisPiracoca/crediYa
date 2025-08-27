package pragma.crediya.user.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.dto.response.UserListResponseDto;
import pragma.crediya.user.application.handler.UserHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final UserHandler userHandler;

    @PostMapping
    public Mono<ResponseEntity<RegisterUserResponseDto>> registerUser(@RequestBody @Valid RegisterUserDto userDto) {
        return userHandler.handleRegisterUser(userDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @GetMapping
    public Flux<UserListResponseDto> listUsers() {
        return userHandler.handleListAllUsers();
    }
}