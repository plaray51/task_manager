package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("spring.datasource.url", dotenv.get("ORACLE_DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("ORACLE_DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("ORACLE_DB_PASSWORD"));

        SpringApplication.run(DemoApplication.class, args);
    }
}