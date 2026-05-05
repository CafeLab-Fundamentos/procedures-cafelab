package com.acme.procedurescafelab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProceduresCafeLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProceduresCafeLabApplication.class, args);
    }

}
