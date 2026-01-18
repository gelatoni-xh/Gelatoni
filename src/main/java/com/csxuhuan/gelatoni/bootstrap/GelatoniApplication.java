package com.csxuhuan.gelatoni.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.csxuhuan.gelatoni")
@MapperScan("com.csxuhuan.gelatoni.infrastructure.repository.mapper")
public class GelatoniApplication {
    public static void main(String[] args) {
        SpringApplication.run(GelatoniApplication.class, args);
    }
}
