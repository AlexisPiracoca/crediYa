package pragma.crediya.user.domain.ports;

import pragma.crediya.user.domain.model.AuthenticationRequest;
import pragma.crediya.user.domain.model.AuthenticationResponse;
import reactor.core.publisher.Mono;

public interface AuthenticationRepository {
    Mono<AuthenticationResponse> authenticate(AuthenticationRequest request);
    Mono<Boolean> validateUser(String email, String password);
    Mono<String> generateToken(String email);
}
