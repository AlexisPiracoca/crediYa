package pragma.crediya.user.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.infrastructure.entity.UserEntity;
import pragma.crediya.user.infrastructure.mapper.UserMapper;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper = new UserMapper();

    @Test
    @DisplayName("Debe convertir UserEntity a User correctamente")
    void shouldConvertEntityToDomain() {
        UserEntity entity = new UserEntity(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);

        User result = userMapper.toDomain(entity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Juan");
        assertThat(result.getLastName()).isEqualTo("Pérez");
        assertThat(result.getEmail()).isEqualTo("juan@test.com");
        assertThat(result.getSalary()).isEqualTo(5000000.0);
        assertThat(result.getPhone()).isEqualTo("123456789");
        assertThat(result.getAddress()).isEqualTo("Calle 123");
        assertThat(result.getDateBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("Debe convertir User a UserEntity correctamente")
    void shouldConvertDomainToEntity() {
        User user = new User(1L, "Juan", "Pérez", LocalDate.of(1990, 1, 1),
                "Calle 123", "123456789", "juan@test.com", 5000000.0);

        UserEntity result = userMapper.toEntity(user);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Juan");
        assertThat(result.getLastName()).isEqualTo("Pérez");
        assertThat(result.getEmail()).isEqualTo("juan@test.com");
        assertThat(result.getSalary()).isEqualTo(5000000.0);
        assertThat(result.getPhone()).isEqualTo("123456789");
        assertThat(result.getAddress()).isEqualTo("Calle 123");
        assertThat(result.getDateBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }
}
