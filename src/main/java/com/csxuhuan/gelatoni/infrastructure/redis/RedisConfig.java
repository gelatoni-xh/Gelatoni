package com.csxuhuan.gelatoni.infrastructure.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置类
 * 配置StringRedisTemplate，以JSON字符串形式存储缓存，保证类型安全与可读性
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * 配置StringRedisTemplate实例
     * 
     * @param connectionFactory Redis连接工厂
     * @return 配置好的StringRedisTemplate实例
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}

