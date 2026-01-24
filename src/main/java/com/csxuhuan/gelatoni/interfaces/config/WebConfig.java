package com.csxuhuan.gelatoni.interfaces.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 *
 * <p>配置 Web 层的全局设置，包括：
 * <ul>
 *     <li>拦截器注册与路径匹配</li>
 *     <li>跨域配置（如需要）</li>
 *     <li>静态资源处理（如需要）</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * 注册拦截器
     *
     * <p>将认证拦截器注册到 /api/** 路径下的所有请求。
     * 拦截器会检查带有 {@link AuthCheck} 注解的方法是否携带有效 Token。
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**"); // 拦截 /api 下所有接口
    }
}