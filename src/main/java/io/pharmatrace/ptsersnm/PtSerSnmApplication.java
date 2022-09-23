package io.pharmatrace.ptsersnm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EntityScan("io.pharmatrace.ptsersnm.context.orm")

@SpringBootApplication
public class PtSerSnmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtSerSnmApplication.class, args);
    }

}
