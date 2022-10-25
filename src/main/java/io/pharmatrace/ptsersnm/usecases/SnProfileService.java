package io.pharmatrace.ptsersnm.usecases;

import ch.qos.logback.core.rolling.helper.MonoTypedConverter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.pharmatrace.ptsersnm.context.orm.SnProfileRepository;
import io.pharmatrace.ptsersnm.exceptions.ApiExceptionHandler;
import io.pharmatrace.ptsersnm.exceptions.ApiRequestException;
import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.time.Instant;
import java.util.UUID;


@Service
public class SnProfileService implements CRUDQBService<SnProfile, UUID>  {

    @Autowired
    SnProfileRepository snProfileRepository;

    @Lazy
    @Autowired
    SerialNumberService serialNumberService;
    
    private final boolean NOT_DELETED= false;

    public Flux<SnProfile> getAllProfiles(){
        return snProfileRepository.findAllByIsDelete(NOT_DELETED);
    }


    @Override
    public Mono<SnProfile> create(Mono<SnProfile> businessObj) {

        return businessObj.flatMap(entity ->{
            return snProfileRepository.existsByNameAndIsDelete(entity.getName(), NOT_DELETED).flatMap(nameExist->{
                if(nameExist){
                    throw new ApiRequestException("Profile with name "+entity.getName()+" already exists!");
                }else{
                    return snProfileRepository.existsByIdentifierAndIsDelete(entity.getIdentifier(), NOT_DELETED).flatMap(identifierExist->{
                        if(identifierExist){
                            throw new ApiRequestException("Profile with identifier "+entity.getIdentifier()+" already exists!");
                        }
                        entity.init();
                        Mono<SnProfile> snProfileMono = CRUDQBService.super.create(Mono.just(entity)).flatMap(x->{

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    serialNumberService.generateNumbers(x.getId(), x.getMaxRequestSize(), true)
                                    .subscribe();
                                }
                            });
                            thread.start();
//
                            return  Mono.just(x);
                        });


                        return snProfileMono;
                    });
                }
            });
        });
    }

    @Override
    public Mono<SnProfile> update(Mono<SnProfile> businessObj) {

        return businessObj.flatMap(entity -> {
            return snProfileRepository.existsByIdAndAndIsDelete(entity.getId(),NOT_DELETED).flatMap(idExist->{
                if(!idExist){
                    throw new ApiRequestException("Profile with name "+entity.getName()+" did not found!");
                }
                return CRUDQBService.super.update(Mono.just(entity));
            });
        });
    }


    public Mono<SnProfile> getProfileByName(String name){

        return snProfileRepository.existsByNameAndIsDelete(name, NOT_DELETED).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByNameAndIsDelete(name, NOT_DELETED);
            }else{
                throw new ApiRequestException("No profile found with profile name "+name);
            }
        });

    }

    public Mono<SnProfile> getProfileByIdentifier(String identifier){
        return snProfileRepository.existsByIdentifierAndIsDelete(identifier, NOT_DELETED).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByIdentifierAndIsDelete(identifier, NOT_DELETED);
            }else{
                throw new ApiRequestException("No profile found with profile identifier "+identifier);
            }
        });
    }

    public Mono<SnProfile> getProfileById(UUID id){
        return snProfileRepository.existsByIdAndAndIsDelete(id,NOT_DELETED).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByIdAndIsDelete(id, NOT_DELETED);
            }else{
                throw new ApiRequestException("No profile found with profile id "+id);
            }
        });
    }

    public Mono<Boolean> existsByName(String name){
        return snProfileRepository.existsByNameAndIsDelete(name, NOT_DELETED);
    }

    public Mono<Boolean> existsIdentifier(String identifier){
        return snProfileRepository.existsByIdentifierAndIsDelete(identifier, NOT_DELETED);
    }

    public Mono<SnProfile> updateMetadata(SnProfile profile) throws RuntimeException{

        return snProfileRepository.existsByIdAndAndIsDelete(profile.getId(), NOT_DELETED).flatMap(isExist->{
            if(isExist){
                Mono<SnProfile> businessObj = snProfileRepository.getSnProfileByIdAndIsDelete(profile.getId(), NOT_DELETED);
                return  businessObj.flatMap(entity -> {
                    entity.setProfileMetadata(profile.getProfileMetadata());
                    return snProfileRepository.save(entity);
                });
            }else{
                throw new ApiRequestException("No profile found with profile id "+profile.getId());
            }
        });
    }

    public Mono<SnProfile> deleteProfile(SnProfile profile) {

        return snProfileRepository.existsByIdAndAndIsDelete(profile.getId(),NOT_DELETED).flatMap(isExist->{
            if(isExist){
                Mono<SnProfile> businessObj = snProfileRepository.getSnProfileByIdAndIsDelete(profile.getId(), NOT_DELETED);

                return businessObj.flatMap(entity -> {
                    entity.setIsDelete(true);
                    serialNumberService.disableOnDelete(entity.getId()).subscribe();
                    return snProfileRepository.save(entity);
                });
            }else{
                throw new ApiRequestException("No profile found with profile id "+profile.getId());
            }
        });
    }

    public Mono<Void> deleteMulipleProfiles(Iterable<UUID> profilesIds) {


        return snProfileRepository.deleteAllById(profilesIds).doOnSuccess(x->{

            serialNumberService.disableOnMultipleDelete(profilesIds).subscribe();
        });

    }


    @Override
    public R2dbcRepository<SnProfile, UUID> getRepository() {
        return snProfileRepository;
    }



}
