package pragma.crediya.user.application.handler;

import lombok.*;
import org.springframework.stereotype.Component;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.mapper.UserDtoMapper;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.dto.response.UserListResponseDto;
import pragma.crediya.user.application.useCase.GetAllUsersUseCase;
import pragma.crediya.user.application.useCase.RegisterUserUseCase;
import pragma.crediya.user.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final RegisterUserUseCase registerUserUseCase;
    private final UserDtoMapper dtoMapper;
    private final GetAllUsersUseCase getAllUsersUseCase;

    public Mono<RegisterUserResponseDto> handleRegisterUser(RegisterUserDto requestDto) {
        User domainUser = dtoMapper.toDomain(requestDto);

        return registerUserUseCase.register(domainUser)
                .map(dtoMapper::toRegisterResponseDto);
    }

    public Flux<UserListResponseDto> handleListAllUsers() {
        return registerUserUseCase.listAllUsers()
                .map(dtoMapper::toListResponseDto);
    }

    public Flux<User> handleGetAllUsers() {
        return getAllUsersUseCase.execute();
    }
}
