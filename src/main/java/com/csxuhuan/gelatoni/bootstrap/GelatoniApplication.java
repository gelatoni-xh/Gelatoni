package com.csxuhuan.gelatoni.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Gelatoni 应用程序启动类
 *
 * <p>这是 Spring Boot 应用的入口点。项目采用 DDD（领域驱动设计）分层架构：
 * <ul>
 *     <li>interfaces - 接口层：Controller、Config、DTO、Request</li>
 *     <li>application - 应用层：AppService，编排领域服务</li>
 *     <li>domain - 领域层：Entity、DomainService、Query、Result</li>
 *     <li>infrastructure - 基础设施层：Repository、Mapper、DO</li>
 *     <li>bootstrap - 启动层：应用配置和启动</li>
 * </ul>
 *
 * <p>技术栈：
 * <ul>
 *     <li>Spring Boot - Web 框架</li>
 *     <li>MyBatis-Plus - ORM 框架</li>
 *     <li>Spring Cloud Sleuth - 链路追踪</li>
 * </ul>
 *
 * @author csxuhuan
 */
@SpringBootApplication(scanBasePackages = "com.csxuhuan.gelatoni")
@MapperScan("com.csxuhuan.gelatoni.infrastructure.repository.mapper")
public class GelatoniApplication {

    /**
     * 应用程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(GelatoniApplication.class, args);
    }
}
