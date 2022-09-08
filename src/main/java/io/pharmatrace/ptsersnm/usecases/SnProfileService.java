package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.context.orm.SnProfileRepository;
import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Instant;


@Service
public class SnProfileService {

    @Autowired
    SnProfileRepository snProfileRepository;

    public Flux<SnProfile> getAllProfiles(){
        return snProfileRepository.findAll();
    }

    public Mono<SnProfile> saveProfile(SnProfile snProfile){
        snProfile.setCreatedOn(Instant.now());
        return snProfileRepository.save(snProfile);
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


}
