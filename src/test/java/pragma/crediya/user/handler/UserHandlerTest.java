package pragma.crediya.user.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.dto.request.UserDtoMapper;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.handler.UserHandler;
import pragma.crediya.user.application.useCase.RegisterUserUseCase;
import pragma.crediya.user.domain.model.User;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class serHandlerTest {

    @Mock
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private UserDtoMapper dtoMapper;

    @InjectMocks
    private UserHandler userHandler;

    private RegisterUserDto requestDto;
    private User domainUser;
    private RegisterUserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new RegisterUserDto("Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);

        domainUser = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);

        responseDto = new RegisterUserResponseDto(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);
    }

    @Test
    @DisplayName("Debe manejar el registro de usuario exitosamente")
    void shouldHandleRegisterUserSuccessfully() {
        when(dtoMapper.toDomain(requestDto)).thenReturn(domainUser);
        when(registerUserUseCase.register(domainUser)).thenReturn(Mono.just(domainUser));
        when(dtoMapper.toRegisterResponseDto(domainUser)).thenReturn(responseDto);

        Mono<RegisterUserResponseDto> result = userHandler.handleRegisterUser(requestDto);

        StepVerifier.create(result)
                .expectNext(responseDto)
                .verifyComplete();

        verify(dtoMapper).toDomain(requestDto);
        verify(registerUserUseCase).register(domainUser);
        verify(dtoMapper).toRegisterResponseDto(domainUser);
    }
}