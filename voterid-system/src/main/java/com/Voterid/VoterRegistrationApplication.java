package com.Voterid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Voter Registration System API",
        version = "1.0",
        description = "API for managing voter registration and related services"
    )
)

public class VoterRegistrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoterRegistrationApplication.class, args);
        
    }
 

}