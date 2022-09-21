package io.pharmatrace.ptsersnm.context.routes;

import io.pharmatrace.ptsersnm.context.orm.SnProfileRepository;
import io.pharmatrace.ptsersnm.model.SerialNumber;
import io.pharmatrace.ptsersnm.usecases.SerialNumberGeneratorService;
import io.pharmatrace.ptsersnm.usecases.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

@RestController("/serialNumberGenerator")
public class SerialNumberGeneratorController {

    @Autowired
    SerialNumberGeneratorService serialNumberGeneratorService;

    @Autowired
    SerialNumberService serialNumberService;

    final String alphaNumericString = "ABCDEFGHIJKLMNOPQURSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    @GetMapping("/generateSerialNumber/{profileId}/{requestSize}")
    public Flux<SerialNumber> generateSerialNumber(@PathVariable UUID profileId, @PathVariable int requestSize){

        List<SerialNumber> sList = serialNumberGeneratorService.generateNumbers( alphaNumericString, false, requestSize, 30);
        serialNumberService.saveAll(sList).subscribe();
        return null;
    }

    @GetMapping("/getSerialNumbers/{profileId}/{requestSize}")
    public Flux<SerialNumber> getSerialNumbers(@PathVariable UUID profileId, @PathVariable int requestSize){

        List<SerialNumber> sList = serialNumberGeneratorService.generateNumbers2(profileId, alphaNumericString, false, requestSize, 30);
        serialNumberService.saveAll(sList).subscribe();
        return null;
    }

    @PostMapping("/saveSerialNumber")
    Mono<SerialNumber> saveSerialNumber(@RequestBody SerialNumber serialNumber){
        return serialNumberService.create(Mono.just(serialNumber));
    }

    @GetMapping("/existById/{id}")
    Mono<Boolean> existById(@PathVariable Long id){

        return serialNumberService.existById(id);
    }

    @GetMapping("/getAll")
    Flux<SerialNumber> getAll(){

        return serialNumberService.findAll();
    }



}
