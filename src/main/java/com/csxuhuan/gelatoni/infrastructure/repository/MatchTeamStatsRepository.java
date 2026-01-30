package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;

import java.util.List;

/**
 * 比赛队伍统计仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 MatchTeamStats
 *
 * @author Gelatoni
 */
public interface MatchTeamStatsRepository {

    /**
     * 根据比赛ID查询队伍统计数据
     *
     * @param matchId 比赛ID
     * @return 队伍统计数据列表（领域对象）
     */
    List<MatchTeamStats> findByMatchId(Long matchId);

    /**
     * 根据比赛ID查询我方队伍统计数据
     *
     * @param matchId 比赛ID
     * @return 我方队伍统计数据列表（领域对象）
     */
    List<MatchTeamStats> findMyTeamStatsByMatchId(Long matchId);

    /**
     * 根据比赛ID查询对方队伍统计数据
     *
     * @param matchId 比赛ID
     * @return 对方队伍统计数据列表（领域对象）
     */
    List<MatchTeamStats> findOpponentTeamStatsByMatchId(Long matchId);

    /**
     * 批量新增队伍统计数据
     *
     * @param teamStatsList 新增的队伍统计数据列表
     * @param creator 创建人ID
     * @return 新增的记录数
     */
    int batchCreate(List<MatchTeamStats> teamStatsList, Long creator);

    /**
     * 批量更新队伍统计数据
     *
     * @param teamStatsList 更新的队伍统计数据列表
     * @param modifier 修改人ID
     * @return 更新的记录数
     */
    int batchUpdate(List<MatchTeamStats> teamStatsList, Long modifier);

    /**
     * 根据比赛ID删除队伍统计数据（软删除）
     *
     * @param matchId 比赛ID
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteByMatchId(Long matchId, Long modifier);

    /**
     * 根据比赛ID和队伍类型删除队伍统计数据（软删除）
     *
     * @param matchId 比赛ID
     * @param teamType 队伍类型
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteByMatchIdAndTeamType(Long matchId, Integer teamType, Long modifier);
}