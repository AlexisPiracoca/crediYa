package pragma.crediya.user.useCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pragma.crediya.user.application.useCase.RegisterUserUseCase;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.domain.ports.UserRepository;
import pragma.crediya.user.infrastructure.entity.UserEntity;
import pragma.crediya.user.infrastructure.mapper.UserMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    private User testUser;
    private UserEntity testUserEntity;
    private UserEntity savedUserEntity;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(null);
        testUser.setName("Juan");
        testUser.setLastName("Pérez");
        testUser.setEmail("juan@test.com");
        testUser.setSalary(5000000.0);
        testUser.setPhone("123456789");
        testUser.setAddress("Calle 123");
        testUser.setDateBirth(LocalDate.of(1990, 1, 1));

        testUserEntity = new UserEntity();
        testUserEntity.setId(null);
        testUserEntity.setName("Juan");
        testUserEntity.setLastName("Pérez");
        testUserEntity.setEmail("juan@test.com");
        testUserEntity.setSalary(5000000.0);
        testUserEntity.setPhone("123456789");
        testUserEntity.setAddress("Calle 123");
        testUserEntity.setDateBirth(LocalDate.of(1990, 1, 1));

        savedUserEntity = new UserEntity();
        savedUserEntity.setId(1L);
        savedUserEntity.setName("Juan");
        savedUserEntity.setLastName("Pérez");
        savedUserEntity.setEmail("juan@test.com");
        savedUserEntity.setSalary(5000000.0);
        savedUserEntity.setPhone("123456789");
        savedUserEntity.setAddress("Calle 123");
        savedUserEntity.setDateBirth(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("Debe registrar el usuario exitosamente")
    void shouldRegisterUserSuccessfully_WhenEmailDoesNotExist() {
        User expectedUser = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);

        when(userRepository.existsByEmail("juan@test.com")).thenReturn(Mono.just(false));
        when(userMapper.toEntity(testUser)).thenReturn(testUserEntity);
        when(userRepository.save(testUserEntity)).thenReturn(Mono.just(savedUserEntity));
        when(userMapper.toDomain(savedUserEntity)).thenReturn(expectedUser);

        Mono<User> result = registerUserUseCase.register(testUser);

        StepVerifier.create(result)
                .expectNext(expectedUser)
                .verifyComplete();

        verify(userRepository).existsByEmail("juan@test.com");
        verify(userMapper).toEntity(testUser);
        verify(userRepository).save(testUserEntity);
        verify(userMapper).toDomain(savedUserEntity);
    }

    @Test
    @DisplayName("Debe fallar cuando el email ya esta registrado")
    void shouldFailToRegisterUser_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail("juan@test.com")).thenReturn(Mono.just(true));

        Mono<User> result = registerUserUseCase.register(testUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("El correo ya está en uso"))
                .verify();

        verify(userRepository).existsByEmail("juan@test.com");
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toEntity(any());
        verify(userMapper, never()).toDomain(any());
    }
}
