package com.csxuhuan.gelatoni.interfaces.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 配置类
 *
 * <p>配置 OpenAPI 3.0 文档生成，包括：
 * <ul>
 *     <li>API 安全认证方案（Bearer Token）</li>
 *     <li>全局安全要求配置</li>
 * </ul>
 *
 * <p>访问路径：
 * <ul>
 *     <li>Swagger UI: /swagger-ui.html</li>
 *     <li>OpenAPI JSON: /v3/api-docs</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置 OpenAPI 文档
     *
     * <p>设置 Bearer Token 认证方案，使 Swagger UI 支持在请求中携带认证信息。
     * 所有 API 默认都需要认证（可在具体接口上覆盖）。
     *
     * @return OpenAPI 配置对象
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                );
    }
}