package pragma.crediya.user.application.mapper;

import org.springframework.stereotype.Component;
import pragma.crediya.user.application.dto.request.LoginRequestDto;
import pragma.crediya.user.application.dto.response.LoginResponseDto;
import pragma.crediya.user.application.dto.response.UserBasicInfoDto;
import pragma.crediya.user.domain.model.AuthenticationRequest;
import pragma.crediya.user.domain.model.AuthenticationResponse;

@Component
public class AuthDtoMapper {

    public AuthenticationRequest toDomain(LoginRequestDto dto) {
        return new AuthenticationRequest(dto.getEmail(), dto.getPassword());
    }

    public LoginResponseDto toResponseDto(AuthenticationResponse domain) {
        UserBasicInfoDto userDto = null;
        if (domain.getUser() != null) {
            userDto = new UserBasicInfoDto(
                    domain.getUser().getId(),
                    domain.getUser().getName(),
                    domain.getUser().getEmail(),
                    domain.getUser().getRol() != null ? domain.getUser().getRol().getName() : null
            );
        }

        return new LoginResponseDto(
                domain.getToken(),
                domain.getMessage(),
                domain.isSuccess(),
                userDto
        );
    }
}