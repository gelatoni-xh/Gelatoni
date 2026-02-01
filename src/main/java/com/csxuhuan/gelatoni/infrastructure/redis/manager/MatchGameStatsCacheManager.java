package com.csxuhuan.gelatoni.infrastructure.redis.manager;

import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.infrastructure.redis.RedisClient;
import com.csxuhuan.gelatoni.infrastructure.redis.generator.GenericCacheKeyGenerator;
import com.csxuhuan.gelatoni.infrastructure.redis.generator.MatchGameStatsCacheKeyGenerator;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 比赛统计数据缓存管理器
 * 
 * <p>负责比赛统计数据的缓存操作，包括获取、设置、清除等
 * 默认缓存时间为30分钟，可根据业务需求调整
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Component
public class MatchGameStatsCacheManager {

    private final RedisClient redisClient;
    private final MatchGameStatsCacheKeyGenerator keyGenerator;
    private final GenericCacheKeyGenerator genericKeyGenerator;

    /** 默认缓存时间：30分钟 */
    private static final Duration DEFAULT_CACHE_TTL = Duration.ofMinutes(30);

    public MatchGameStatsCacheManager(RedisClient redisClient, 
                                    MatchGameStatsCacheKeyGenerator keyGenerator,
                                    GenericCacheKeyGenerator genericKeyGenerator) {
        this.redisClient = redisClient;
        this.keyGenerator = keyGenerator;
        this.genericKeyGenerator = genericKeyGenerator;
    }

    /**
     * 获取缓存的统计数据
     * 
     * @param cacheKey 缓存键
     * @return 缓存的统计数据，如果不存在则返回null
     */
    public MatchGameStatsDTO getStats(String cacheKey) {
        return redisClient.get(cacheKey, MatchGameStatsDTO.class);
    }

    /**
     * 设置统计数据到缓存
     * 
     * @param cacheKey 缓存键
     * @param stats 统计数据
     */
    public void setStats(String cacheKey, MatchGameStatsDTO stats) {
        redisClient.set(cacheKey, stats, DEFAULT_CACHE_TTL);
    }

    /**
     * 清除指定键的缓存
     * 
     * @param cacheKey 缓存键
     * @return 是否删除成功
     */
    public Boolean evict(String cacheKey) {
        return redisClient.delete(cacheKey);
    }

    /**
     * 清除所有统计数据缓存
     * 在数据发生重大变更时调用
     */
    public void evictAllStats() {
        // 注意：RedisTemplate的keys操作在生产环境中需要谨慎使用
        // 这里简化处理，实际项目中建议使用scan命令或专门的缓存清理机制
        String pattern = keyGenerator.getAllStatsPattern();
        // 实际实现可能需要遍历匹配的key并删除
        // 这里先留空，后续可以根据具体Redis客户端实现
    }

    /**
     * 清除指定赛季的统计数据缓存
     * 在特定赛季数据变更时调用
     * 
     * @param season 赛季标识
     */
    public void evictSeasonStats(String season) {
        // 同上，需要根据具体的Redis实现来处理pattern匹配删除
        String pattern = keyGenerator.getSeasonStatsPattern(season);
        // 实际删除逻辑待实现
    }

    /**
     * 检查缓存是否存在
     * 
     * @param cacheKey 缓存键
     * @return 是否存在
     */
    public Boolean exists(String cacheKey) {
        return redisClient.exists(cacheKey);
    }

    /**
     * 设置自定义缓存时间
     * 
     * @param cacheKey 缓存键
     * @param stats 统计数据
     * @param ttl 缓存时间
     */
    public void setStatsWithCustomTtl(String cacheKey, MatchGameStatsDTO stats, Duration ttl) {
        redisClient.set(cacheKey, stats, ttl);
    }

    // ==================== 通用缓存方法 ====================

    /**
     * 通用获取缓存方法
     * 
     * @param key 缓存键
     * @param type 返回类型
     * @param <T> 泛型类型
     * @return 缓存值
     */
    public <T> T getGeneric(String key, Class<T> type) {
        return redisClient.get(key, type);
    }

    /**
     * 通用设置缓存方法
     * 
     * @param key 缓存键
     * @param value 缓存值
     * @param ttl 过期时间
     */
    public void setGeneric(String key, Object value, Duration ttl) {
        redisClient.set(key, value, ttl);
    }

    /**
     * 生成通用缓存键
     * 
     * @param businessType 业务类型
     * @param subType 子类型
     * @param params 参数
     * @return 缓存键
     */
    public String generateKey(String businessType, String subType, Object... params) {
        return genericKeyGenerator.generateKey(businessType, subType, params);
    }
}