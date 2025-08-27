package pragma.crediya.user.infrastructure.adapter.out;

import org.springframework.stereotype.Repository;
import pragma.crediya.user.domain.ports.UserRepository;
import pragma.crediya.user.infrastructure.entity.UserEntity;
import pragma.crediya.user.infrastructure.repository.UserJpaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return jpaRepository.save(userEntity);
    }

    @Override
    public Flux<UserEntity> findAll() {
        return jpaRepository.findAll();
    }
}
