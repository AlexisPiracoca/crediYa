package pragma.crediya.user.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pragma.crediya.user.domain.model.Rol;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.infrastructure.entity.UserEntity;

@Component
public class UserMapper {

    public UserEntity toEntity(User domain) {
        return new UserEntity(
                domain.getId(),
                domain.getName(),
                domain.getLastName(),
                domain.getDateBirth(),
                domain.getAddress(),
                domain.getPhone(),
                domain.getEmail(),
                domain.getSalary(),
                domain.getPassword(),
                domain.getRol() != null ? domain.getRol().getId() : null
        );
    }

    public User toDomain(UserEntity entity) {
        Rol rol = new Rol();
        rol.setId(entity.getRolId());

        return new User(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getDateBirth(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getSalary(),
                entity.getPassword(),
                rol
        );
    }
}