package com.csxuhuan.gelatoni.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis客户端工具类
 * 提供常用的Redis操作方法，包括基础的键值操作和计数器功能
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Component
public class RedisClient {

    /** Redis模板实例，用于执行各种Redis操作 */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数
     * 
     * @param redisTemplate Redis模板实例
     */
    public RedisClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /* ---------- 基础 KV 操作 ---------- */

    /**
     * 设置键值对并指定过期时间
     * 
     * @param key 键
     * @param value 值
     * @param ttl 过期时间，null表示永不过期
     */
    public void set(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * 获取指定键的值并转换为指定类型
     * 
     * @param key 键
     * @param type 返回值的类型
     * @param <T> 泛型类型
     * @return 转换后的值，如果键不存在则返回null
     */
    public <T> T get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return type.cast(value);
    }

    /**
     * 删除指定的键
     * 
     * @param key 要删除的键
     * @return Boolean.TRUE表示删除成功，Boolean.FALSE表示键不存在，null表示操作失败
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 检查指定的键是否存在
     * 
     * @param key 要检查的键
     * @return true表示键存在，false表示键不存在
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /* ---------- 计数 / 状态类操作 ---------- */

    /**
     * 对指定键的值进行原子递增操作
     * 如果键不存在，则创建新键并设置初始值为delta
     * 当创建新键时，可以设置过期时间
     * 
     * @param key 要递增的键
     * @param delta 递增量（可以为负数）
     * @param ttl 过期时间，仅在创建新键时生效，null表示永不过期
     * @return 递增后的新值
     */
    public Long increment(String key, long delta, Duration ttl) {
        Long value = redisTemplate.opsForValue().increment(key, delta);
        // 如果是新建的键（返回值等于delta）且设置了过期时间，则设置过期时间
        if (value != null && value == delta && ttl != null) {
            redisTemplate.expire(key, ttl);
        }
        return value;
    }
}

