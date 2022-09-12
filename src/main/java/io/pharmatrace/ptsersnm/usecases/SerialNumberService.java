package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.context.orm.SerialNumberRepository;
import io.pharmatrace.ptsersnm.exceptions.ApiRequestException;
import io.pharmatrace.ptsersnm.model.SerialNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class SerialNumberService implements CRUDQBService<SerialNumber, Long>{

    @Autowired
    SerialNumberRepository serialNumberRepository;


    @Override
    public R2dbcRepository<SerialNumber, Long> getRepository() {
        return serialNumberRepository;
    }


    @Override
    public Mono<SerialNumber> create(Mono<SerialNumber> businessObj) {

        return businessObj.flatMap(entity ->{
            return CRUDQBService.super.create(Mono.just(entity));
        });

    }

    public Flux<SerialNumber> saveAll(Iterable<SerialNumber> entities){
        long startTime     = System.currentTimeMillis();
        Flux<SerialNumber> list = serialNumberRepository.saveAll(entities);
        Logger.getGlobal()
                .info(String.format("persisted serial numbers in %d ms.",
                        System.currentTimeMillis() - startTime));

        return list;
    }


    public Mono<Boolean> existById(long id){
        return serialNumberRepository.existsById(id);
    }

    public Flux<SerialNumber> findAll(){
        return serialNumberRepository.findAll();
    }


}
