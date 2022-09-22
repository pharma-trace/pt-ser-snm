package io.pharmatrace.ptsersnm.context.orm;

import io.pharmatrace.ptsersnm.model.SerialNumber;
import org.springframework.data.domain.Example;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Repository
public interface SerialNumberRepository extends R2dbcRepository<SerialNumber, Long> {
    @Override
    <S extends SerialNumber> Flux<S> saveAll(Iterable<S> entities);

    @Override
    Mono<Boolean> existsById(Long aLong);


    @Query("select * from serial_numbers where is_used=:isUsed limit :requestSize")
   Flux<SerialNumber> findAllByIsUsed(boolean isUsed, long requestSize);
}
