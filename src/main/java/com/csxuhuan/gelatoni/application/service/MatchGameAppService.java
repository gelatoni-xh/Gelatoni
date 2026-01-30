package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;

import java.util.List;

/**
 * 比赛应用服务接口
 *
 * <p>应用层服务，提供比赛相关的业务操作。
 * 作为 interfaces 层与 domain 层之间的桥梁。
 *
 * <p>支持的操作：
 * <ul>
 *     <li>创建比赛</li>
 *     <li>更新比赛</li>
 *     <li>删除比赛</li>
 *     <li>查询比赛</li>
 *     <li>分页查询比赛列表</li>
 *     <li>查询比赛详情</li>
 * </ul>
 *
 * @author Gelatoni
 */
public interface MatchGameAppService {

    /**
     * 创建比赛
     *
     * <p>支持两种创建方式：
     * <ul>
     *     <li>只保存比赛基础信息</li>
     *     <li>一次性保存整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     *
     * @param query 创建查询对象
     * @return 创建的比赛ID
     */
    Long createMatchGame(MatchGameCreateQuery query);

    /**
     * 更新比赛
     *
     * <p>支持两种更新方式：
     * <ul>
     *     <li>只更新比赛基础信息</li>
     *     <li>一次性更新整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     *
     * @param query 更新查询对象
     * @return 更新成功标志
     */
    boolean updateMatchGame(MatchGameUpdateQuery query);

    /**
     * 删除比赛
     *
     * <p>删除比赛记录，同时删除该比赛对应的队伍统计数据和球员数据。
     *
     * @param id 比赛ID
     * @return 删除成功标志
     */
    boolean deleteMatchGame(Long id);

    /**
     * 根据ID获取比赛
     *
     * @param id 比赛ID
     * @return 比赛DTO
     */
    MatchGameDTO getMatchGameById(Long id);

    /**
     * 分页查询比赛列表
     *
     * <p>按比赛时间排序，支持按赛季筛选。
     *
     * @param query 分页查询对象
     * @return 比赛列表
     */
    List<MatchGameDTO> getMatchGamePage(MatchGamePageQuery query);

    /**
     * 获取比赛总数
     *
     * @return 比赛总数
     */
    int getMatchGameCount();

    /**
     * 获取比赛详情
     *
     * <p>返回比赛基础信息、队伍统计数据和球员统计数据。
     *
     * @param id 比赛ID
     * @return 比赛详情DTO
     */
    MatchGameDetailDTO getMatchGameDetail(Long id);

    /**
     * 获取比赛数据统计（仅我方数据）
     *
     * <p>不做预计算/缓存/中间表；通过一次查询 + 内存聚合生成各榜单。
     *
     * @param request 统计请求
     * @return 统计结果
     */
    MatchGameStatsDTO getMatchGameStats(MatchGameStatsRequest request);
}