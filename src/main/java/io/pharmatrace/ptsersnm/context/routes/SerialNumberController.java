package io.pharmatrace.ptsersnm.context.routes;

import io.pharmatrace.ptsersnm.model.SerialNumber;
import io.pharmatrace.ptsersnm.usecases.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController("/serialNumberGenerator")
@CrossOrigin(origins = "*")
public class SerialNumberController {

    @Autowired
    SerialNumberService serialNumberService;

//    @GetMapping("/generateSerialNumber/{profileId}")
//    public Flux<SerialNumber> generateSerialNumber(@PathVariable UUID profileId){
//        Flux<SerialNumber> sList = serialNumberService.generateNumbers( profileId,true);
//        return sList;
//    }

    @GetMapping("/getSerialNumbers/{profileId}/{requestSize}")
    public Flux<SerialNumber> getSerialNumbers(@PathVariable UUID profileId, @PathVariable int requestSize){
         return serialNumberService.getSerialNumbers(profileId, requestSize);
    }

    @PostMapping("/saveSerialNumber")
    Mono<SerialNumber> saveSerialNumber(@RequestBody SerialNumber serialNumber){
        return serialNumberService.create(Mono.just(serialNumber));
    }

    @GetMapping("/existById/{id}")
    Mono<Boolean> existById(@PathVariable Long id){

        return serialNumberService.existById(id);
    }


}
