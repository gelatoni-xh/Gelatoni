package com.csxuhuan.gelatoni.infrastructure.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Environment Properties Loader
 * 用于加载 .env 文件中的环境变量到 Spring Boot 的环境中
 */
public class EnvironmentPropertiesLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        // 尝试加载 .env 文件
        try {
            FileSystemResource envResource = new FileSystemResource("env");
            if (envResource.exists()) {
                Properties properties = new Properties();
                String envContent = StreamUtils.copyToString(envResource.getInputStream(), StandardCharsets.UTF_8);

                // 解析 .env 文件内容
                String[] lines = envContent.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    String[] keyValue = line.split("=", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        // 移除可能的引号
                        if (value.startsWith("\"") && value.endsWith("\"")) {
                            value = value.substring(1, value.length() - 1);
                        }
                        properties.setProperty(key, value);
                    }
                }

                // 将属性添加到 Spring 环境
                environment.getPropertySources().addFirst(new org.springframework.core.env.PropertiesPropertySource("env", properties));
            }
        } catch (IOException e) {
            // 忽略 .env 文件不存在的情况
        }
    }
}