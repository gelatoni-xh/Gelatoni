package com.csxuhuan.gelatoni.application.assembler;

import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchTeamStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchPlayerStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameUpdateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGamePageRequest;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛 Assembler
 * 负责 Request/DTO 与 Domain 对象之间的转换
 *
 * @author Gelatoni
 */
public class MatchGameAssembler {

    /**
     * 将前端创建请求转换为领域查询对象
     *
     * @param request 前端创建请求
     * @return 领域查询对象
     */
    public MatchGameCreateQuery toDomainQuery(MatchGameCreateRequest request) {
        return new MatchGameCreateQuery(
                request.getSeason(),
                request.getSeasonMatchNo(),
                request.getMatchTime(),
                request.getIsRobot(),
                request.getMyScore(),
                request.getOppScore(),
                request.getResult(),
                request.getRemark(),
                request.getCreator(),
                !CollectionUtils.isEmpty(request.getTeamStatsList()) ?
                    request.getTeamStatsList().stream()
                        .map(teamStat -> new MatchTeamStats(
                            null, null, teamStat.getTeamType(), teamStat.getScore(), 
                            teamStat.getFgAttempt(), teamStat.getFgMade(), 
                            teamStat.getThreeAttempt(), teamStat.getThreeMade(), 
                            teamStat.getAssist(), teamStat.getRebound(), 
                            teamStat.getOffRebound(), teamStat.getDefRebound(), 
                            teamStat.getSteal(), teamStat.getBlock(), 
                            teamStat.getDunk(), teamStat.getPaintScore(), 
                            teamStat.getSecondChanceScore(), teamStat.getTurnoverToScore(), 
                            teamStat.getMaxLead(), request.getCreator(), request.getCreator(), 
                            null, null))
                        .collect(Collectors.toList()) : null,
                !CollectionUtils.isEmpty(request.getPlayerStatsList()) ?
                    request.getPlayerStatsList().stream()
                        .map(playerStat -> new MatchPlayerStats(
                            null, null, playerStat.getTeamType(), playerStat.getUserName(), 
                            playerStat.getPlayerName(), playerStat.getRating(), 
                            playerStat.getIsMvp(), playerStat.getIsSvp(), 
                            playerStat.getScore(), playerStat.getAssist(), 
                            playerStat.getRebound(), playerStat.getSteal(), 
                            playerStat.getBlock(), playerStat.getTurnover(), 
                            playerStat.getDunk(), playerStat.getFgAttempt(), 
                            playerStat.getFgMade(), playerStat.getThreeAttempt(), 
                            playerStat.getThreeMade(), playerStat.getMidCount(), 
                            playerStat.getMaxScoringRun(), request.getCreator(), 
                            request.getCreator(), null, null))
                        .collect(Collectors.toList()) : null
        );
    }

    /**
     * 将前端更新请求转换为领域查询对象
     *
     * @param request 前端更新请求
     * @return 领域查询对象
     */
    public MatchGameUpdateQuery toDomainQuery(MatchGameUpdateRequest request) {
        return new MatchGameUpdateQuery(
                request.getId(),
                request.getSeason(),
                request.getSeasonMatchNo(),
                request.getMatchTime(),
                request.getIsRobot(),
                request.getMyScore(),
                request.getOppScore(),
                request.getResult(),
                request.getRemark(),
                request.getModifier(),
                request.getTeamStatsList() != null ?
                    request.getTeamStatsList().stream()
                        .map(teamStat -> new MatchTeamStats(
                            teamStat.getId(), null, teamStat.getTeamType(), teamStat.getScore(),
                            teamStat.getFgAttempt(), teamStat.getFgMade(),
                            teamStat.getThreeAttempt(), teamStat.getThreeMade(),
                            teamStat.getAssist(), teamStat.getRebound(),
                            teamStat.getOffRebound(), teamStat.getDefRebound(),
                            teamStat.getSteal(), teamStat.getBlock(),
                            teamStat.getDunk(), teamStat.getPaintScore(),
                            teamStat.getSecondChanceScore(), teamStat.getTurnoverToScore(),
                            teamStat.getMaxLead(), null, request.getModifier(),
                            null, null))
                        .collect(Collectors.toList()) : null,
                request.getPlayerStatsList() != null ?
                    request.getPlayerStatsList().stream()
                        .map(playerStat -> new MatchPlayerStats(
                            playerStat.getId(), null, playerStat.getTeamType(), playerStat.getUserName(),
                            playerStat.getPlayerName(), playerStat.getRating(),
                            playerStat.getIsMvp(), playerStat.getIsSvp(),
                            playerStat.getScore(), playerStat.getAssist(),
                            playerStat.getRebound(), playerStat.getSteal(),
                            playerStat.getBlock(), playerStat.getTurnover(),
                            playerStat.getDunk(), playerStat.getFgAttempt(),
                            playerStat.getFgMade(), playerStat.getThreeAttempt(),
                            playerStat.getThreeMade(), playerStat.getMidCount(),
                            playerStat.getMaxScoringRun(), null, request.getModifier(),
                            null, null))
                        .collect(Collectors.toList()) : null
        );
    }

    /**
     * 将前端分页请求转换为领域查询对象
     *
     * @param request 前端分页请求
     * @return 领域查询对象
     */
    public MatchGamePageQuery toDomainQuery(MatchGamePageRequest request) {
        return new MatchGamePageQuery(request.getPageNum(), request.getPageSize(), request.getSeason());
    }

    /**
     * 将领域层实体转换为前端展示用 DTO
     *
     * @param game 领域层实体
     * @return 前端展示用 DTO
     */
    public MatchGameDTO toDTO(MatchGame game) {
        if (game == null) {
            return null;
        }
        MatchGameDTO dto = new MatchGameDTO();
        dto.setId(game.getId());
        dto.setSeason(game.getSeason());
        dto.setSeasonMatchNo(game.getSeasonMatchNo());
        dto.setMatchTime(game.getMatchTime());
        dto.setIsRobot(game.getIsRobot());
        dto.setMyScore(game.getMyScore());
        dto.setOppScore(game.getOppScore());
        dto.setResult(game.getResult());
        dto.setRemark(game.getRemark());
        dto.setCreateTime(game.getCreateTime());
        return dto;
    }

    /**
     * 将领域层实体列表转换为前端展示用 DTO 列表
     *
     * @param games 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<MatchGameDTO> toMatchGameDTOList(List<MatchGame> games) {
        return games.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 将领域层队伍统计实体转换为前端展示用 DTO
     *
     * @param teamStats 领域层实体
     * @return 前端展示用 DTO
     */
    public MatchTeamStatsDTO toDTO(MatchTeamStats teamStats) {
        if (teamStats == null) {
            return null;
        }
        MatchTeamStatsDTO dto = new MatchTeamStatsDTO();
        dto.setId(teamStats.getId());
        dto.setMatchId(teamStats.getMatchId());
        dto.setTeamType(teamStats.getTeamType());
        dto.setScore(teamStats.getScore());
        dto.setFgAttempt(teamStats.getFgAttempt());
        dto.setFgMade(teamStats.getFgMade());
        dto.setThreeAttempt(teamStats.getThreeAttempt());
        dto.setThreeMade(teamStats.getThreeMade());
        dto.setAssist(teamStats.getAssist());
        dto.setRebound(teamStats.getRebound());
        dto.setOffRebound(teamStats.getOffRebound());
        dto.setDefRebound(teamStats.getDefRebound());
        dto.setSteal(teamStats.getSteal());
        dto.setBlock(teamStats.getBlock());
        dto.setDunk(teamStats.getDunk());
        dto.setPaintScore(teamStats.getPaintScore());
        dto.setSecondChanceScore(teamStats.getSecondChanceScore());
        dto.setTurnoverToScore(teamStats.getTurnoverToScore());
        dto.setMaxLead(teamStats.getMaxLead());
        dto.setCreateTime(teamStats.getCreateTime());
        return dto;
    }

    /**
     * 将领域层队伍统计实体列表转换为前端展示用 DTO 列表
     *
     * @param teamStatsList 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<MatchTeamStatsDTO> toMatchTeamStatsDTOList(List<MatchTeamStats> teamStatsList) {
        return teamStatsList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 将领域层球员统计实体转换为前端展示用 DTO
     *
     * @param playerStats 领域层实体
     * @return 前端展示用 DTO
     */
    public MatchPlayerStatsDTO toDTO(MatchPlayerStats playerStats) {
        if (playerStats == null) {
            return null;
        }
        MatchPlayerStatsDTO dto = new MatchPlayerStatsDTO();
        dto.setId(playerStats.getId());
        dto.setMatchId(playerStats.getMatchId());
        dto.setTeamType(playerStats.getTeamType());
        dto.setUserName(playerStats.getUserName());
        dto.setPlayerName(playerStats.getPlayerName());
        dto.setRating(playerStats.getRating());
        dto.setIsMvp(playerStats.getIsMvp());
        dto.setIsSvp(playerStats.getIsSvp());
        dto.setScore(playerStats.getScore());
        dto.setAssist(playerStats.getAssist());
        dto.setRebound(playerStats.getRebound());
        dto.setSteal(playerStats.getSteal());
        dto.setBlock(playerStats.getBlock());
        dto.setTurnover(playerStats.getTurnover());
        dto.setDunk(playerStats.getDunk());
        dto.setFgAttempt(playerStats.getFgAttempt());
        dto.setFgMade(playerStats.getFgMade());
        dto.setThreeAttempt(playerStats.getThreeAttempt());
        dto.setThreeMade(playerStats.getThreeMade());
        dto.setMidCount(playerStats.getMidCount());
        dto.setMaxScoringRun(playerStats.getMaxScoringRun());
        dto.setCreateTime(playerStats.getCreateTime());
        return dto;
    }

    /**
     * 将领域层球员统计实体列表转换为前端展示用 DTO 列表
     *
     * @param playerStatsList 领域层实体列表
     * @return 前端展示用 DTO 列表
     */
    public List<MatchPlayerStatsDTO> toMatchPlayerStatsDTOList(List<MatchPlayerStats> playerStatsList) {
        return playerStatsList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 组装比赛详情 DTO
     *
     * @param matchGame 比赛基础信息
     * @param myTeamStats 我方队伍统计数据
     * @param opponentTeamStats 对方队伍统计数据
     * @param myPlayerStats 我方球员数据
     * @param opponentPlayerStats 对方球员数据
     * @return 比赛详情 DTO
     */
    public MatchGameDetailDTO toDetailDTO(MatchGameDTO matchGame, List<MatchTeamStatsDTO> myTeamStats,
                                          List<MatchTeamStatsDTO> opponentTeamStats, List<MatchPlayerStatsDTO> myPlayerStats,
                                          List<MatchPlayerStatsDTO> opponentPlayerStats) {
        return new MatchGameDetailDTO(matchGame, myTeamStats, opponentTeamStats, myPlayerStats, opponentPlayerStats);
    }
}