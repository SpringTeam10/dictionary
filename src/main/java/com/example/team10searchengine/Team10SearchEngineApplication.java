package com.example.team10searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class Team10SearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team10SearchEngineApplication.class, args);
    }

}
