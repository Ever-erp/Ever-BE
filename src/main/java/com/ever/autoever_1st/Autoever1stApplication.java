package com.ever.autoever_1st;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class Autoever1stApplication {

    public static void main(String[] args) {
        SpringApplication.run(Autoever1stApplication.class, args);
    }

}
