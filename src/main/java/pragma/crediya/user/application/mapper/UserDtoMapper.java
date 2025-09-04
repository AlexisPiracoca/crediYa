package pragma.crediya.user.application.mapper;

import org.springframework.stereotype.Component;
import pragma.crediya.user.application.dto.request.RegisterUserDto;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.dto.response.UserListResponseDto;
import pragma.crediya.user.domain.model.Rol;
import pragma.crediya.user.domain.model.User;

@Component
public class UserDtoMapper {

    public User toDomain(RegisterUserDto dto) {
        Rol rol = new Rol();
        rol.setId(dto.getRolId());

        return new User(
                null,
                dto.getName(),
                dto.getLastName(),
                dto.getDateBirth(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getSalary(),
                dto.getPassword(),
                rol
        );
    }

    public RegisterUserResponseDto toRegisterResponseDto(User domain) {
        return new RegisterUserResponseDto(
                domain.getId(),
                domain.getName(),
                domain.getLastName(),
                domain.getDateBirth(),
                domain.getAddress(),
                domain.getPhone(),
                domain.getEmail(),
                domain.getSalary(),
                domain.getRol() != null ? domain.getRol().getId() : null
        );
    }

    public UserListResponseDto toListResponseDto(User domain) {
        return new UserListResponseDto(
                domain.getId(),
                domain.getName(),
                domain.getLastName(),
                domain.getDateBirth(),
                domain.getAddress(),
                domain.getPhone(),
                domain.getEmail(),
                domain.getSalary(),
                domain.getRol() != null ? domain.getRol().getId() : null
        );
    }
}

