package io.pharmatrace.ptsersnm.context.orm;

import io.pharmatrace.ptsersnm.model.SerialNumber;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface SerialNumberRepository extends R2dbcRepository<SerialNumber, Long> {
    @Override
    <S extends SerialNumber> Flux<S> saveAll(Iterable<S> entities);

    @Override
    <S extends SerialNumber> Flux<S> saveAll(Publisher<S> entityStream);

    @Override
    Mono<Boolean> existsById(Long aLong);

    @Query("select * from serial_numbers where profile_id=:profileId AND status=:status order by sn_index limit :requestSize")
   Flux<SerialNumber> findAllByStatus(UUID profileId, String status, long requestSize);

    @Query("update serial_numbers set status=:status where profile_id=:profileId AND  sn_index between :startIndex and :endIndex")
    Flux<SerialNumber> udateStatus(UUID profileId, long startIndex, long endIndex, String status);

    @Query("update serial_numbers set status='DISABLED' where profile_id=:profileId AND status='AVAILABLE'")
    Flux<SerialNumber> disableOnDelete(UUID profileId);

    @Query("update serial_numbers set status='DISABLED' where profile_id in (:profileIds) AND status='AVAILABLE'")
    Flux<SerialNumber> disableOnMultipleDelete(Iterable<? extends UUID> profileIds);

    @Query("select count(*) from serial_numbers where profile_id=:profileId AND status='AVAILABLE'")
    Mono<Integer> countSerials(UUID profileId);

//    DELETE FROM sn_profile WHERE sn_profile.profile_id IN ($1, $2, $3)
}
