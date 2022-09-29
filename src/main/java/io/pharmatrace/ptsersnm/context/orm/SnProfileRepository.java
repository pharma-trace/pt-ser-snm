package io.pharmatrace.ptsersnm.context.orm;

import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SnProfileRepository extends R2dbcRepository<SnProfile, UUID> {

    public Flux<SnProfile> findAllByIsDelete(Boolean isDelete);

    public Mono<SnProfile> getSnProfileByNameAndIsDelete(String name, Boolean isDelete);

    public Mono<SnProfile> getSnProfileByIdentifierAndIsDelete(String identifier, Boolean isDelete);

    Mono<SnProfile> getSnProfileByIdAndIsDelete(UUID id, Boolean isDelete);

    public Mono<Boolean> existsByNameAndIsDelete(String name, Boolean isDelete);

    public Mono<Boolean> existsByIdentifierAndIsDelete(String identifier, Boolean isDelete);

    Mono<Boolean> existsByIdAndAndIsDelete(UUID uuid, Boolean isDelete);

    @Override
    @Query("UPDATE sn_profile SET is_delete=true WHERE sn_profile.profile_id IN (:uuid)")
    Mono<Void> deleteAllById(Iterable<? extends UUID> uuids);


}
