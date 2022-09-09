package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.model.BaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface CRUDQBService<T extends BaseEntity,PK> {

    R2dbcRepository<T, PK> getRepository();

    default Mono<T> create(Mono<T> businessObj) {
        return businessObj.flatMap( entity-> {
            entity.setCreatedOn(Instant.now());
            entity.setUpdatedOn(Instant.now());
            return getRepository().save(entity);
        });
    }

    default Mono<T> read(PK id) {
        return getRepository().findById(id);
    }

    default Mono<T> update(Mono<T> businessObj) {
        return businessObj.flatMap(entity->{
            if (entity.getId() == null) {
                return Mono.empty();
            }
            entity.setUpdatedOn(Instant.now());
            return getRepository().save(entity);
        });
    }

    default Mono<PK> delete(PK id) {
        return (Mono<PK>) read(id).flatMap(entity-> {
            entity.setIsDelete(Boolean.TRUE);
            entity.setUpdatedOn(Instant.now());
            return update(Mono.just(entity)).map(T::getId);
        });
    }

    default Flux<T> browse() { return getRepository().findAll(); }

}