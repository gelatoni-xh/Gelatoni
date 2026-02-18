package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameBaseDataDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameTrendDTO;
import com.csxuhuan.gelatoni.application.dto.OpponentStatsDTO;
import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameUpdateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameTrendRequest;
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
     * 分页查询比赛列表
     *
     * <p>按比赛时间排序，支持按赛季筛选。
     *
     * @param query 分页查询对象
     * @return 比赛列表
     */
    List<MatchGameDTO> getMatchGamePage(MatchGamePageQuery query);

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
     * 本方法已集成缓存机制，相同参数的请求会命中缓存。
     *
     * @param request 统计请求
     * @return 统计结果
     */
    MatchGameStatsDTO getMatchGameStats(MatchGameStatsRequest request);

    /**
     * 获取比赛趋势数据（按日期分组统计）
     *
     * <p>按日期统计比赛平均胜率、球员平均评分、得分、篮板、助攻、抢断、盖帽。
     *
     * @param request 趋势统计请求
     * @return 趋势统计结果
     */
    MatchGameTrendDTO getMatchGameTrend(MatchGameTrendRequest request);

    /**
     * 获取对手统计数据
     *
     * <p>统计对阵各个对手的胜负情况，排除机器人比赛。
     *
     * @param season 赛季（可选，为空表示全赛季）
     * @param minGames 最小对阵次数过滤（默认3）
     * @return 对手统计结果
     */
    OpponentStatsDTO getOpponentStats(String season, Integer minGames);

    /**
     * 获取比赛基础数据
     *
     * @return 基础数据
     */
    MatchGameBaseDataDTO getMatchGameBaseData();

    /**
     * 校验比赛创建请求数据
     *
     * @param request 比赛创建请求
     * @throws BizException 当数据不一致时抛出业务异常
     */
    void validateCreateRequest(MatchGameCreateRequest request);

    /**
     * 校验比赛更新请求数据
     *
     * @param request 比赛更新请求
     * @throws BizException 当数据不一致时抛出业务异常
     */
    void validateUpdateRequest(MatchGameUpdateRequest request);
}