package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameBaseDataDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.application.service.MatchGameAppService;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameUpdateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGamePageRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;
import com.csxuhuan.gelatoni.application.assembler.MatchGameAssembler;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 比赛控制器
 *
 * <p>提供比赛相关的 REST API 接口：
 * <ul>
 *     <li>POST /api/match-game/create - 保存比赛接口</li>
 *     <li>DELETE /api/match-game/delete/{id} - 删除比赛接口</li>
 *     <li>POST /api/match-game/page - 分页查询比赛列表接口</li>
 *     <li>GET /api/match-game/detail/{id} - 查询单场比赛详情接口</li>
 * </ul>
 *
 * @author Gelatoni
 */
@RestController
@RequestMapping("/api/match-game")
public class MatchGameController {

    private final MatchGameAppService matchGameAppService;
    private final MatchGameAssembler assembler = new MatchGameAssembler();

    /**
     * 构造函数，注入依赖服务
     *
     * @param matchGameAppService 比赛应用服务
     */
    public MatchGameController(MatchGameAppService matchGameAppService) {
        this.matchGameAppService = matchGameAppService;
    }

    /**
     * 保存比赛接口
     *
     * <p>用于保存比赛数据。
     * 支持两种保存方式：
     * <ul>
     *     <li>只保存比赛基础信息</li>
     *     <li>一次性保存整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     * 注意：不允许只保存一部分球员数据。
     *
     * @param request 比赛创建请求
     * @return 创建的比赛ID
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Long> create(@RequestBody MatchGameCreateRequest request) {
        // 先进行DTO级别数据校验
        matchGameAppService.validateCreateRequest(request);
        
        MatchGameCreateQuery query = assembler.toDomainQuery(request);
        Long matchId = matchGameAppService.createMatchGame(query);
        return BaseResponse.success(matchId);
    }

    /**
     * 更新比赛接口
     *
     * <p>用于更新比赛数据。
     * 支持两种更新方式：
     * <ul>
     *     <li>只更新比赛基础信息</li>
     *     <li>一次性更新整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
     * </ul>
     * 注意：不允许只更新一部分球员数据。
     *
     * @param request 比赛更新请求
     * @return 更新成功标志
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Boolean> update(@RequestBody MatchGameUpdateRequest request) {
        // 先进行DTO级别数据校验
        matchGameAppService.validateUpdateRequest(request);
        
        MatchGameUpdateQuery query = assembler.toDomainQuery(request);
        Boolean result = matchGameAppService.updateMatchGame(query);
        return BaseResponse.success(result);
    }

    /**
     * 删除比赛接口
     *
     * <p>用于删除一场比赛。
     * 删除比赛记录，同时删除该比赛对应的队伍统计数据和球员数据。
     *
     * @param id 比赛ID
     * @return 删除成功标志
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Boolean> delete(@PathVariable Long id) {
        Boolean result = matchGameAppService.deleteMatchGame(id);
        return BaseResponse.success(result);
    }

    /**
     * 分页查询比赛列表接口
     *
     * <p>用于分页展示比赛结果。
     * 支持分页，按比赛时间排序，支持按赛季筛选，只返回比赛基础信息。
     *
     * @param request 比赛分页查询请求
     * @return 比赛列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<MatchGameDTO>> page(@RequestBody MatchGamePageRequest request) {
        MatchGamePageQuery query = assembler.toDomainQuery(request);
        List<MatchGameDTO> matchGames = matchGameAppService.getMatchGamePage(query);
        return BaseResponse.success(matchGames);
    }

    /**
     * 查询单场比赛详情接口
     *
     * <p>用于查看一场比赛的完整统计数据。
     * 返回比赛基础信息、我方队伍整体统计数据、对方队伍整体统计数据、我方全部球员数据、对方全部球员数据。
     *
     * @param id 比赛ID
     * @return 比赛详情
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @GetMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<MatchGameDetailDTO> detail(@PathVariable Long id) {
        MatchGameDetailDTO matchGameDetail = matchGameAppService.getMatchGameDetail(id);
        return BaseResponse.success(matchGameDetail);
    }

    /**
     * 比赛数据统计接口
     *
     * <p>用于比赛数据分析页面的基础统计能力。
     * 支持：
     * <ul>
     *     <li>全赛季 / 指定赛季（season 为空表示全赛季）</li>
     *     <li>统计维度：按用户维度、按球员维度</li>
     * </ul>
     *
     * <p>设计约束：
     * <ul>
     *     <li>只统计我方数据（team_type=1）</li>
     *     <li>不做预计算/缓存/中间表；一次查询 + 聚合计算得到结果</li>
     * </ul>
     *
     * @param request 统计请求
     * @return 统计结果
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @PostMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<MatchGameStatsDTO> stats(@RequestBody MatchGameStatsRequest request) {
        MatchGameStatsDTO data = matchGameAppService.getMatchGameStats(request);
        return BaseResponse.success(data);
    }

    /**
     * 获取比赛基础数据接口
     *
     * @return 基础数据
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_MATCH)
    @GetMapping(value = "/base-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<MatchGameBaseDataDTO> baseData() {
        MatchGameBaseDataDTO data = matchGameAppService.getMatchGameBaseData();
        return BaseResponse.success(data);
    }
}