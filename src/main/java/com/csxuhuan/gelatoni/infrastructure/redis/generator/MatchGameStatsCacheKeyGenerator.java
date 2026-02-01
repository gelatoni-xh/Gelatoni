package com.csxuhuan.gelatoni.infrastructure.redis.generator;

import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 比赛统计数据缓存键生成器
 * 
 * <p>基于通用缓存键生成器，专门为比赛统计提供缓存键生成功能
 * 缓存键格式：match:stats:{season}:{excludeRobot}:{dimension}
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Component
public class MatchGameStatsCacheKeyGenerator {

    private final GenericCacheKeyGenerator genericGenerator;

    public MatchGameStatsCacheKeyGenerator(GenericCacheKeyGenerator genericGenerator) {
        this.genericGenerator = genericGenerator;
    }

    /**
     * 根据统计请求生成缓存键
     * 
     * @param request 统计请求参数
     * @return 缓存键字符串
     */
    public String generateKey(MatchGameStatsRequest request) {
        if (request == null) {
            return genericGenerator.generateMatchStatsKey(null, true, "user");
        }

        String season = StringUtils.hasText(request.getSeason()) ? request.getSeason() : null;
        Boolean excludeRobot = request.getExcludeRobot();
        String dimension = request.getDimension() != null ? request.getDimension().name() : null;

        return genericGenerator.generateMatchStatsKey(season, excludeRobot, dimension);
    }

    /**
     * 生成清除所有统计缓存的键模式
     * 用于当数据发生变化时清除相关缓存
     * 
     * @return 缓存键模式
     */
    public String getAllStatsPattern() {
        return genericGenerator.generatePattern("match", "stats");
    }

    /**
     * 根据赛季生成清除缓存的键模式
     * 用于当特定赛季数据变化时清除相关缓存
     * 
     * @param season 赛季标识
     * @return 缓存键模式
     */
    public String getSeasonStatsPattern(String season) {
        String seasonParam = StringUtils.hasText(season) ? season : null;
        return genericGenerator.generatePattern("match", "stats", seasonParam);
    }
}