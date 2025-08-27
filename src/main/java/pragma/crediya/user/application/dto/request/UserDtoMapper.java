package pragma.crediya.user.application.dto.request;

import org.springframework.stereotype.Component;
import pragma.crediya.user.application.dto.response.RegisterUserResponseDto;
import pragma.crediya.user.application.dto.response.UserListResponseDto;
import pragma.crediya.user.domain.model.User;

@Component
public class UserDtoMapper {

    public User toDomain(RegisterUserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setDateBirth(dto.getDateBirth());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setSalary(dto.getSalary());
        return user;
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
                domain.getSalary()
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
                domain.getSalary()
        );
    }
}
