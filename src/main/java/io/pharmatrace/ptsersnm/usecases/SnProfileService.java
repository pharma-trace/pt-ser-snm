package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.context.orm.SnProfileRepository;
import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Instant;
import java.util.UUID;


@Service
public class SnProfileService implements CRUDQBService<SnProfile, UUID>  {

    @Autowired
    SnProfileRepository snProfileRepository;

    public Flux<SnProfile> getAllProfiles(){
        return snProfileRepository.findAllByIsDelete(false);
    }

    //--------------------------
    @Override
    public Mono<SnProfile> create(Mono<SnProfile> businessObj) {
        return businessObj.flatMap(entity ->CRUDQBService.super.create(Mono.just(entity)));
    }

    @Override
    public Mono<SnProfile> update(Mono<SnProfile> businessObj) {
        return businessObj.flatMap(entity -> CRUDQBService.super.update(Mono.just(entity)));
    }


    public Mono<SnProfile> getProfileByName(String name){
        return snProfileRepository.getSnProfileByName(name);
    }

    public Mono<SnProfile> getProfileByIdentifier(String identifier){
        return snProfileRepository.getSnProfileByIdentifier(identifier);
    }

    public Mono<Boolean> existsByName(String name){
        return snProfileRepository.existsByName(name);
    }

    public Mono<Boolean> existsIdentifier(String identifier){
        return snProfileRepository.existsByIdentifier(identifier);
    }

    public Mono<SnProfile> updateMetadata(SnProfile profile) {
        Mono<SnProfile> businessObj = snProfileRepository.getSnProfileById(profile.getId());

        return businessObj.flatMap(entity -> {
            entity.setProfileMetadata(profile.getProfileMetadata());
            return snProfileRepository.save(entity);
        });
    }

    public Mono<SnProfile> deleteProfile(SnProfile profile) {
        Mono<SnProfile> businessObj = snProfileRepository.getSnProfileById(profile.getId());

        return businessObj.flatMap(entity -> {
            entity.setIsDelete(true);
            return snProfileRepository.save(entity);
        });
    }

    @Override
    public R2dbcRepository<SnProfile, UUID> getRepository() {
        return snProfileRepository;
    }
}
