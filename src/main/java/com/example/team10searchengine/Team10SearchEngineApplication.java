package com.example.team10searchengine;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing // 배치 사용을 위한 선언
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class Team10SearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team10SearchEngineApplication.class, args);
    }

}
