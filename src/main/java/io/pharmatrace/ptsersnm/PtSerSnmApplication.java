package io.pharmatrace.ptsersnm;

import io.pharmatrace.ptsersnm.model.SerialNumber;
import io.pharmatrace.ptsersnm.usecases.SerialNumberGeneratorService;
import io.pharmatrace.ptsersnm.usecases.SerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

//@EntityScan("io.pharmatrace.ptsersnm.context.orm")

@SpringBootApplication
public class PtSerSnmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtSerSnmApplication.class, args);
    }



}
