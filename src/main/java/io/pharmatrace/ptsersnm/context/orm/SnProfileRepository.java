package io.pharmatrace.ptsersnm.context.orm;

import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SnProfileRepository extends R2dbcRepository<SnProfile, UUID> {

    public Flux<SnProfile> findAllByIsDelete(Boolean isDelete);

    public Mono<SnProfile> getSnProfileByNameAndIsDelete(String name, Boolean isDelete);

    public Mono<SnProfile> getSnProfileByIdentifierAndIsDelete(String identifier, Boolean isDelete);

    public Mono<SnProfile> getSnProfileById(UUID id);

    public Mono<Boolean> existsByName(String name);

    public Mono<Boolean> existsByIdentifier(String identifier);

    Mono<Boolean> existsById(UUID uuid);

}
