package com.csxuhuan.gelatoni;

import com.csxuhuan.gelatoni.infrastructure.config.EnvironmentPropertiesLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class GelatoniApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GelatoniApplication.class);

        // 添加环境属性加载器
        app.addListeners(new ApplicationListener<ApplicationEnvironmentPreparedEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
                new EnvironmentPropertiesLoader().onApplicationEvent(event);
            }
        });

        app.run(args);
    }
}