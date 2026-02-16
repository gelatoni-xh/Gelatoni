package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;

import java.util.List;

/**
 * 比赛球员统计仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 MatchPlayerStats
 *
 * @author Gelatoni
 */
public interface MatchPlayerStatsRepository {

    /**
     * 根据比赛ID查询球员统计数据
     *
     * @param matchId 比赛ID
     * @return 球员统计数据列表（领域对象）
     */
    List<MatchPlayerStats> findByMatchId(Long matchId);

    /**
     * 查询我方球员统计明细（用于数据统计聚合）
     *
     * <p>只统计我方数据（team_type=1）。
     * season 为空/空字符串时表示全赛季。
     *
     * @param season 赛季（可选）
     * @param excludeRobot 是否排除机器人
     * @param matchDate 比赛日期（可选），格式：yyyy-MM-dd，游戏时间8:00-2:00
     * @return 我方球员统计明细列表
     */
    List<MatchPlayerStats> findMyPlayerStatsForStats(String season, Boolean excludeRobot, String matchDate);

    /**
     * 批量新增球员统计数据
     *
     * @param playerStatsList 新增的球员统计数据列表
     * @param creator 创建人ID
     * @return 新增的记录数
     */
    int batchCreate(List<MatchPlayerStats> playerStatsList, Long creator);

    /**
     * 根据比赛ID删除球员统计数据（软删除）
     *
     * @param matchId 比赛ID
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteByMatchId(Long matchId, Long modifier);

    /**
     * 查询球员名称列表（去重）
     *
     * @param teamType 队伍类型：1=我方，2=对方
     * @return 球员名称列表
     */
    List<String> findDistinctPlayerNames(Integer teamType);

    /**
     * 查询我方用户昵称列表（去重）
     *
     * @return 我方用户昵称列表
     */
    List<String> findDistinctMyUserNames();

    /**
     * 查询对方球员统计明细（用于对手统计）
     *
     * <p>只统计对方数据（team_type=2）。
     * season 为空/空字符串时表示全赛季。
     *
     * @param season 赛季（可选）
     * @param excludeRobot 是否排除机器人
     * @return 对方球员统计明细列表
     */
    List<MatchPlayerStats> findOpponentPlayerStatsForStats(String season, Boolean excludeRobot);
}