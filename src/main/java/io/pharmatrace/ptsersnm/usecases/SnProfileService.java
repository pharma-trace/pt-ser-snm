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

    public Flux<SnProfile> getAllProfiles(){
        return snProfileRepository.findAllByIsDelete(false);
    }


    @Override
    public Mono<SnProfile> create(Mono<SnProfile> businessObj) {

        return businessObj.flatMap(entity ->{
            return snProfileRepository.existsByName(entity.getName()).flatMap(nameExist->{
                if(nameExist){
                    throw new ApiRequestException("Profile with name "+entity.getName()+" already exists!");
                }else{
                    return snProfileRepository.existsByIdentifier(entity.getIdentifier()).flatMap(identifierExist->{
                        if(identifierExist){
                            throw new ApiRequestException("Profile with identifier "+entity.getIdentifier()+" already exists!");
                        }
                        entity.init();
                        Mono<SnProfile> snProfileMono = CRUDQBService.super.create(Mono.just(entity)).doOnNext(profile->{
//                            serialNumberService.generateNumbers(profile.getId(), true);
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
            return snProfileRepository.existsByIdAndAndIsDelete(entity.getId(),false).flatMap(idExist->{
                if(!idExist){
                    throw new ApiRequestException("Profile with name "+entity.getName()+" did not found!");
                }
                return CRUDQBService.super.update(Mono.just(entity));
            });
        });
    }


    public Mono<SnProfile> getProfileByName(String name){

        return snProfileRepository.existsByName(name).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByNameAndIsDelete(name, false);
            }else{
                throw new ApiRequestException("No profile found with profile name "+name);
            }
        });

    }

    public Mono<SnProfile> getProfileByIdentifier(String identifier){
        return snProfileRepository.existsByIdentifier(identifier).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByIdentifierAndIsDelete(identifier, false);
            }else{
                throw new ApiRequestException("No profile found with profile identifier "+identifier);
            }
        });
    }

    public Mono<SnProfile> getProfileById(UUID id){
        return snProfileRepository.existsByIdAndAndIsDelete(id,false).flatMap(isExist->{
            if(isExist){
                return snProfileRepository.getSnProfileByIdAndIsDelete(id, false);
            }else{
                throw new ApiRequestException("No profile found with profile id "+id);
            }
        });
    }

    public Mono<Boolean> existsByName(String name){
        return snProfileRepository.existsByName(name);
    }

    public Mono<Boolean> existsIdentifier(String identifier){
        return snProfileRepository.existsByIdentifier(identifier);
    }

    public Mono<SnProfile> updateMetadata(SnProfile profile) throws RuntimeException{

        return snProfileRepository.existsByIdAndAndIsDelete(profile.getId(), false).flatMap(isExist->{
            if(isExist){
                Mono<SnProfile> businessObj = snProfileRepository.getSnProfileById(profile.getId());
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

        return snProfileRepository.existsByIdAndAndIsDelete(profile.getId(),false).flatMap(isExist->{
            if(isExist){
                Mono<SnProfile> businessObj = snProfileRepository.getSnProfileById(profile.getId());

                return businessObj.flatMap(entity -> {
                    entity.setIsDelete(true);
                    serialNumberService.disableOnDelete(entity.getId());
                    return snProfileRepository.save(entity);
                });
            }else{
                throw new ApiRequestException("No profile found with profile id "+profile.getId());
            }
        });
    }

    @Override
    public R2dbcRepository<SnProfile, UUID> getRepository() {
        return snProfileRepository;
    }



}
