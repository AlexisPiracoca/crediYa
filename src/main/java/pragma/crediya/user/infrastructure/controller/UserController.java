package pragma.crediya.user.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pragma.crediya.user.application.RegisterUserService;
import pragma.crediya.user.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final RegisterUserService registerUserService;

    @PostMapping
    public Mono<ResponseEntity<User>> registerUser(@RequestBody @Valid User user) {
        return registerUserService.register(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

    @GetMapping
    public Flux<User> listUsers() {
        return registerUserService.listAllUsers();
    }
}