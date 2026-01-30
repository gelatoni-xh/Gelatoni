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
     * 根据比赛ID查询我方球员统计数据
     *
     * @param matchId 比赛ID
     * @return 我方球员统计数据列表（领域对象）
     */
    List<MatchPlayerStats> findMyPlayerStatsByMatchId(Long matchId);

    /**
     * 根据比赛ID查询对方球员统计数据
     *
     * @param matchId 比赛ID
     * @return 对方球员统计数据列表（领域对象）
     */
    List<MatchPlayerStats> findOpponentPlayerStatsByMatchId(Long matchId);

    /**
     * 批量新增球员统计数据
     *
     * @param playerStatsList 新增的球员统计数据列表
     * @param creator 创建人ID
     * @return 新增的记录数
     */
    int batchCreate(List<MatchPlayerStats> playerStatsList, Long creator);

    /**
     * 批量更新球员统计数据
     *
     * @param playerStatsList 更新的球员统计数据列表
     * @param modifier 修改人ID
     * @return 更新的记录数
     */
    int batchUpdate(List<MatchPlayerStats> playerStatsList, Long modifier);

    /**
     * 根据比赛ID删除球员统计数据（软删除）
     *
     * @param matchId 比赛ID
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteByMatchId(Long matchId, Long modifier);

    /**
     * 根据比赛ID和队伍类型删除球员统计数据（软删除）
     *
     * @param matchId 比赛ID
     * @param teamType 队伍类型
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteByMatchIdAndTeamType(Long matchId, Integer teamType, Long modifier);
}