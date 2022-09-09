package io.pharmatrace.ptsersnm.context.routes;

import io.pharmatrace.ptsersnm.exceptions.ApiRequestException;
import io.pharmatrace.ptsersnm.model.SnProfile;
import io.pharmatrace.ptsersnm.usecases.SnProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@RestController("/")
public class SnProfileController {

    @Autowired
    SnProfileService snProfileService;

    @GetMapping("getAllProfiles")
    public Flux<SnProfile> getAllProfiles(){
        return snProfileService.getAllProfiles();
    }

    @PostMapping("createProfile")
    public Mono<SnProfile> saveProfile(@RequestBody Mono<SnProfile> snProfile){
        return snProfileService.create(snProfile);
    }

    @PostMapping("updateProfile")
    public Mono<SnProfile> updateProfile(@RequestBody Mono<SnProfile> snProfile){
        return snProfileService.update(snProfile);
    }

    @GetMapping("getProfileByName/{name}")
    public Mono<SnProfile> getProfileByName(@PathVariable String name){
        return snProfileService.getProfileByName(name);
    }

    @GetMapping("getProfileByIdentifier/{identifier}")
    public Mono<SnProfile> getProfileByIdentifier(@PathVariable String identifier){
        return snProfileService.getProfileByIdentifier(identifier);
    }

    @GetMapping("isProfileNameTaken/{name}")
    public Mono<Boolean> isProfileNameAvailable(@PathVariable String name){
        return snProfileService.existsByName(name);
    }

    @GetMapping("isProfileIdentifierTaken/{identifier}")
    public Mono<Boolean> isProfileIdentifierAvailable(@PathVariable String identifier){
        return snProfileService.existsIdentifier(identifier);
    }

    @PostMapping("updateMetadata")
    public Mono<?> updateMetaata(@RequestBody SnProfile profile){
//        throw new ApiRequestException("this api throws exceptoin");
        return snProfileService.updateMetadata(profile);

    }

    @PostMapping("deleteProfile")
    public Mono<SnProfile> deleteProfile(@RequestBody SnProfile profile){
        return snProfileService.deleteProfile(profile);
    }

}
