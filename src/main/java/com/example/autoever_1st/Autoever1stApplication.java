package com.example.autoever_1st;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Autoever1stApplication {

    public static void main(String[] args) {
        SpringApplication.run(Autoever1stApplication.class, args);
    }

}
