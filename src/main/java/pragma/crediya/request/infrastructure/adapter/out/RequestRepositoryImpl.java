package pragma.crediya.request.infrastructure.adapter.out;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import pragma.crediya.request.domain.ports.RequestRepository;
import pragma.crediya.request.infrastructure.entity.RequestEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    private final R2dbcEntityTemplate template;

    public RequestRepositoryImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return template.exists(
                Query.query(Criteria.where("email").is(email)),
                RequestEntity.class
        );
    }

    @Override
    public Mono<RequestEntity> save(RequestEntity requestEntity) {
        return template.insert(RequestEntity.class)
                .using(requestEntity);
    }

    @Override
    public Flux<RequestEntity> findAll() {
        return template.select(RequestEntity.class).all();
    }

    @Override
    public Flux<RequestEntity> findByEmail(String email) {
        return template.select(RequestEntity.class)
                .matching(Query.query(Criteria.where("email").is(email)))
                .all();
    }

    @Override
    public Mono<RequestEntity> findById(Long id) {
        return template.selectOne(
                Query.query(Criteria.where("id").is(id)),
                RequestEntity.class
        );
    }
}