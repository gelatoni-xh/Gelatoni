package com.csxuhuan.gelatoni.infrastructure.redis.generator;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * 通用缓存键生成器
 * 
 * <p>提供统一的缓存键生成规则，支持多种业务场景
 * 缓存键格式：{businessType}:{subType}:{params...}
 * 
 * @author csxuhuan
 * @since 1.0.0
 */
@Component
public class GenericCacheKeyGenerator {

    /**
     * 生成通用缓存键
     * 
     * @param businessType 业务类型（如 match、user、todo 等）
     * @param subType 子类型（如 stats、list、detail 等）
     * @param params 参数列表
     * @return 缓存键字符串
     */
    public String generateKey(String businessType, String subType, Object... params) {
        StringJoiner joiner = new StringJoiner(":");
        joiner.add(businessType).add(subType);
        
        if (params != null) {
            Arrays.stream(params)
                  .map(param -> param == null ? "null" : param.toString())
                  .forEach(joiner::add);
        }
        
        return joiner.toString();
    }

    /**
     * 生成匹配模式键（用于批量删除）
     * 
     * @param businessType 业务类型
     * @param subType 子类型
     * @param partialParams 部分参数（null表示通配符）
     * @return 匹配模式键
     */
    public String generatePattern(String businessType, String subType, Object... partialParams) {
        StringJoiner joiner = new StringJoiner(":");
        joiner.add(businessType).add(subType);
        
        if (partialParams != null && partialParams.length > 0) {
            Arrays.stream(partialParams)
                  .map(param -> param == null ? "*" : param.toString())
                  .forEach(joiner::add);
        }
        joiner.add("*");
        
        return joiner.toString();
    }

    /**
     * 生成比赛统计缓存键
     */
    public String generateMatchStatsKey(String season, Boolean excludeRobot, String matchDate, String dimension) {
        return generateKey("match", "stats",
            season == null || season.isEmpty() ? "all" : season,
            excludeRobot == null ? "true" : excludeRobot.toString(),
            matchDate == null || matchDate.isEmpty() ? "all" : matchDate,
            dimension == null ? "user" : dimension.toLowerCase());
    }
}