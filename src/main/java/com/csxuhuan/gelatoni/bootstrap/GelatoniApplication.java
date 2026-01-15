package com.csxuhuan.gelatoni.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.csxuhuan.gelatoni")
public class GelatoniApplication {
    public static void main(String[] args) {
        SpringApplication.run(GelatoniApplication.class, args);
    }
}
