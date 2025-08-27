package pragma.crediya.user.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pragma.crediya.user.domain.model.User;
import pragma.crediya.user.infrastructure.entity.UserEntity;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getDateBirth(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getSalary()
        );
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLastName(user.getLastName());
        entity.setDateBirth(user.getDateBirth());
        entity.setAddress(user.getAddress());
        entity.setPhone(user.getPhone());
        entity.setEmail(user.getEmail());
        entity.setSalary(user.getSalary());
        return entity;
    }
}