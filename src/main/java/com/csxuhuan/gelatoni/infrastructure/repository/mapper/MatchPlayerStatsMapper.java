package com.csxuhuan.gelatoni.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchPlayerStatsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * MatchPlayerStatsMapper
 * MyBatis-Plus Mapper，提供 CRUD 接口
 *
 * @author Gelatoni
 */
@Mapper
public interface MatchPlayerStatsMapper extends BaseMapper<MatchPlayerStatsDO> {

    /**
     * 查询我方球员统计明细（用于数据统计聚合）
     *
     * <p>设计说明：
     * <ul>
     *     <li>只统计我方数据：team_type = 1</li>
     *     <li>支持按赛季过滤：season 可为空/空字符串表示全赛季</li>
     *     <li>通过 join match_game 过滤 match_game.is_deleted，确保只统计有效比赛</li>
     * </ul>
     *
     * @param season 赛季（可选）
     * @return 我方球员统计明细列表
     */
    // Removed script-based query, now using Wrapper in repository implementation
    // List<MatchPlayerStatsDO> selectMyPlayerStatsForStats(@Param("season") String season);
}