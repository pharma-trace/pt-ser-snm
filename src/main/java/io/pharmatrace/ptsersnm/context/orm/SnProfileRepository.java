package io.pharmatrace.ptsersnm.context.orm;

import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SnProfileRepository extends R2dbcRepository<SnProfile, UUID> {

    public Mono<SnProfile> getSnProfileByName(String name);

    public Mono<SnProfile> getSnProfileByIdentifier(String identifier);

    public Mono<SnProfile> getSnProfileById(UUID id);

    public Mono<Boolean> existsByName(String name);

    public Mono<Boolean> existsByIdentifier(String identifier);

}
