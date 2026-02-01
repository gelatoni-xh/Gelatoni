package com.csxuhuan.gelatoni.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 配置RedisTemplate的序列化方式，确保数据在Redis中的存储格式统一且可读
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate实例
     * 
     * @param connectionFactory Redis连接工厂
     * @return 配置好的RedisTemplate实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // key使用String序列化器，保证键的可读性和直观性
        template.setKeySerializer(new StringRedisSerializer());

        // value使用JSON序列化器，使存储的数据可读、可查询
        Jackson2JsonRedisSerializer<Object> valueSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);

        // 配置ObjectMapper，禁用默认类型推断以减少存储开销和提高兼容性
        ObjectMapper mapper = new ObjectMapper();
        // 不激活默认类型推断，让Jackson根据实际类型自动处理
        valueSerializer.setObjectMapper(mapper);

        // 设置各种序列化器
        template.setValueSerializer(valueSerializer);           // 普通value序列化
        template.setHashKeySerializer(new StringRedisSerializer());    // Hash的key序列化
        template.setHashValueSerializer(valueSerializer);       // Hash的value序列化

        // 初始化完成后调用，确保配置生效
        template.afterPropertiesSet();
        return template;
    }
}

