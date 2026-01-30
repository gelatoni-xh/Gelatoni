package com.csxuhuan.gelatoni.infrastructure.repository;

import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;

import java.util.List;

/**
 * 比赛仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 MatchGame
 *
 * @author Gelatoni
 */
public interface MatchGameRepository {

    /**
     * 查询所有比赛
     *
     * @return 比赛列表（领域对象）
     */
    List<MatchGame> findAll();

    /**
     * 根据ID查询比赛
     *
     * @param id 比赛ID
     * @return 比赛（领域对象）
     */
    MatchGame findById(Long id);

    /**
     * 根据赛季查询比赛列表
     *
     * @param season 赛季标识
     * @return 比赛列表（领域对象）
     */
    List<MatchGame> findBySeason(String season);

    /**
     * 新增比赛
     *
     * @param game 新增的比赛领域对象
     * @param creator 创建人ID
     * @return 新增的ID
     */
    Long create(MatchGame game, Long creator);

    /**
     * 更新比赛
     *
     * @param game 更新的比赛领域对象
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int update(MatchGame game, Long modifier);

    /**
     * 删除比赛（软删除）
     *
     * @param id 比赛ID
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int delete(Long id, Long modifier);

    /**
     * 查询比赛总数
     *
     * @return 比赛总数
     */
    int count();
}