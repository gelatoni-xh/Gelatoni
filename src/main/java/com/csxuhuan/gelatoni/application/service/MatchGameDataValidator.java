package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.MatchPlayerStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchTeamStatsDTO;
import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.application.exception.BizErrorCode;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * 比赛数据校验服务
 *
 * <p>负责校验比赛数据的一致性和完整性，确保录入数据符合业务规则。
 * 设计原则：
 * <ul>
 *     <li>服务于用户的录入习惯，而非限制用户的操作</li>
 *     <li>重点关注数据一致性校验，而非强制性的操作模式</li>
 *     <li>支持机器人对局时不录入对方球员数据的场景</li>
 *     <li>提供全面的数据核对能力</li>
 * </ul>
 *
 * @author Gelatoni
 */
@Service
public class MatchGameDataValidator {

    // ==================== PUBLIC METHODS ====================



    /**
     * 校验比赛创建数据
     *
     * @param query 比赛创建查询对象
     * @throws BizException 当数据不一致时抛出业务异常
     */
    public void validateCreateData(MatchGameCreateQuery query) {
        validateBaseInfo(query);
        
        if (query.getTeamStatsList() != null && !query.getTeamStatsList().isEmpty()) {
            validateTeamStats(query.getTeamStatsList(), query.getIsRobot());
            
            if (query.getPlayerStatsList() != null && !query.getPlayerStatsList().isEmpty()) {
                validatePlayerStats(query.getPlayerStatsList(), query.getIsRobot());
                validateCrossConsistency(query);
            }
        }
    }

    /**
     * 校验比赛更新数据
     *
     * @param query 比赛更新查询对象
     * @throws BizException 当数据不一致时抛出业务异常
     */
    public void validateUpdateData(MatchGameUpdateQuery query) {
        validateBaseInfo(query);
        
        if (query.getTeamStatsList() != null && !query.getTeamStatsList().isEmpty()) {
            validateTeamStats(query.getTeamStatsList(), query.getIsRobot());
            
            if (query.getPlayerStatsList() != null && !query.getPlayerStatsList().isEmpty()) {
                validatePlayerStats(query.getPlayerStatsList(), query.getIsRobot());
                validateCrossConsistency(query);
            }
        }
    }

    /**
     * 转换队伍统计DTO列表
     */
    private List<MatchTeamStats> convertTeamStatsDTOList(List<MatchTeamStatsDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::convertTeamStatsDTO)
                .collect(Collectors.toList());
    }

    /**
     * 转换单个队伍统计DTO
     */
    private MatchTeamStats convertTeamStatsDTO(MatchTeamStatsDTO dto) {
        return new MatchTeamStats(
                null, null, dto.getTeamType(), dto.getScore(),
                dto.getFgAttempt(), dto.getFgMade(), dto.getThreeAttempt(), dto.getThreeMade(),
                dto.getAssist(), dto.getRebound(), dto.getOffRebound(), dto.getDefRebound(),
                dto.getSteal(), dto.getBlock(), dto.getDunk(), dto.getPaintScore(),
                dto.getSecondChanceScore(), dto.getTurnoverToScore(), dto.getMaxLead(),
                null, null, null, null);
    }

    /**
     * 转换球员统计DTO列表
     */
    private List<MatchPlayerStats> convertPlayerStatsDTOList(List<MatchPlayerStatsDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::convertPlayerStatsDTO)
                .collect(Collectors.toList());
    }

    /**
     * 转换单个球员统计DTO
     */
    private MatchPlayerStats convertPlayerStatsDTO(MatchPlayerStatsDTO dto) {
        return new MatchPlayerStats(
                null, null, dto.getTeamType(), dto.getUserName(), dto.getPlayerName(),
                dto.getRating(), dto.getIsMvp(), dto.getIsSvp(), dto.getScore(),
                dto.getAssist(), dto.getRebound(), dto.getSteal(), dto.getBlock(),
                dto.getTurnover(), dto.getDunk(), dto.getFgAttempt(), dto.getFgMade(),
                dto.getThreeAttempt(), dto.getThreeMade(), dto.getMidCount(),
                dto.getMaxScoringRun(), null, null, null, null);
    }

    // ==================== PRIVATE VALIDATION METHODS ====================

    /**
     * 校验比赛基础信息
     */
    private void validateBaseInfo(Object query) {
        // 基础信息校验已在request层面通过注解完成
        // 这里可以添加额外的业务逻辑校验
    }

    /**
     * 校验队伍统计数据
     *
     * @param teamStatsList 队伍统计数据列表
     * @param isRobot 是否为机器人对局
     */
    private void validateTeamStats(List<MatchTeamStats> teamStatsList, Boolean isRobot) {
        // 校验队伍数量
        long myTeamCount = teamStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 1))
                .count();
        long opponentTeamCount = teamStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 2))
                .count();

        if (myTeamCount != 1) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "我方队伍统计数据必须且只能有一条");
        }

        if (opponentTeamCount != 1) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "对方队伍统计数据必须且只能有一条");
        }

        // 校验队伍统计内部数据合理性
        for (MatchTeamStats teamStat : teamStatsList) {
            validateTeamStatInternal(teamStat);
        }
    }

    /**
     * 校验单个队伍统计内部数据合理性
     */
    private void validateTeamStatInternal(MatchTeamStats teamStat) {
        // 投篮数据校验
        if (teamStat.getFgMade() != null && teamStat.getFgAttempt() != null) {
            if (teamStat.getFgMade() > teamStat.getFgAttempt()) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "队伍投篮命中数不能大于投篮尝试数");
            }
        }

        // 三分数据校验
        if (teamStat.getThreeMade() != null && teamStat.getThreeAttempt() != null) {
            if (teamStat.getThreeMade() > teamStat.getThreeAttempt()) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "队伍三分命中数不能大于三分尝试数");
            }
        }

        // 篮板数据校验
        if (teamStat.getRebound() != null && teamStat.getOffRebound() != null && teamStat.getDefRebound() != null) {
            if (!Objects.equals(teamStat.getRebound(), teamStat.getOffRebound() + teamStat.getDefRebound())) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "队伍总篮板数应等于进攻篮板加防守篮板");
            }
        }

        // 得分校验（非负数）
        if (teamStat.getScore() != null && teamStat.getScore() < 0) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "队伍得分不能为负数");
        }

        // 其他统计数据非负数校验
        validateNonNegative(teamStat.getFgAttempt(), "投篮尝试数");
        validateNonNegative(teamStat.getFgMade(), "投篮命中数");
        validateNonNegative(teamStat.getThreeAttempt(), "三分尝试数");
        validateNonNegative(teamStat.getThreeMade(), "三分命中数");
        validateNonNegative(teamStat.getAssist(), "助攻数");
        validateNonNegative(teamStat.getRebound(), "篮板数");
        validateNonNegative(teamStat.getOffRebound(), "进攻篮板数");
        validateNonNegative(teamStat.getDefRebound(), "防守篮板数");
        validateNonNegative(teamStat.getSteal(), "抢断数");
        validateNonNegative(teamStat.getBlock(), "盖帽数");
        validateNonNegative(teamStat.getDunk(), "灌篮次数");
        validateNonNegative(teamStat.getPaintScore(), "内线得分");
        validateNonNegative(teamStat.getSecondChanceScore(), "二次进攻得分");
        validateNonNegative(teamStat.getTurnoverToScore(), "利用失误得分");
    }

    /**
     * 校验球员统计数据
     *
     * @param playerStatsList 球员统计数据列表
     * @param isRobot 是否为机器人对局
     */
    private void validatePlayerStats(List<MatchPlayerStats> playerStatsList, Boolean isRobot) {
        // 按队伍类型分组
        Map<Integer, List<MatchPlayerStats>> teamGroup = playerStatsList.stream()
                .collect(Collectors.groupingBy(MatchPlayerStats::getTeamType));

        List<MatchPlayerStats> myPlayers = teamGroup.getOrDefault(1, java.util.Collections.emptyList());
        List<MatchPlayerStats> opponentPlayers = teamGroup.getOrDefault(2, java.util.Collections.emptyList());

        // 校验我方球员数据（必须且只能有3人）
        if (myPlayers.size() != 3) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "我方球员数据必须且只能有3人");
        }

        // 校验对方球员数据（机器人对局时可以为0人，非机器人对局时必须有3人）
        if (Boolean.TRUE.equals(isRobot)) {
            // 机器人对局：对方球员数据可以为0人或3人
            if (!opponentPlayers.isEmpty() && opponentPlayers.size() != 3) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "机器人对局时，对方球员数据只能为0人或3人");
            }
        } else {
            // 非机器人对局：对方球员数据必须有3人
            if (opponentPlayers.size() != 3) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "非机器人对局时，对方球员数据必须有3人");
            }
        }

        // 校验每个球员的内部数据合理性
        for (MatchPlayerStats playerStat : playerStatsList) {
            validatePlayerStatInternal(playerStat, isRobot);
        }
    }

    /**
     * 校验单个球员统计内部数据合理性
     */
    private void validatePlayerStatInternal(MatchPlayerStats playerStat, Boolean isRobot) {
        // 投篮数据校验
        if (playerStat.getFgMade() != null && playerStat.getFgAttempt() != null) {
            if (playerStat.getFgMade() > playerStat.getFgAttempt()) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "球员投篮命中数不能大于投篮尝试数");
            }
        }

        // 三分数据校验
        if (playerStat.getThreeMade() != null && playerStat.getThreeAttempt() != null) {
            if (playerStat.getThreeMade() > playerStat.getThreeAttempt()) {
                throw new BizException(BizErrorCode.INVALID_PARAM, "球员三分命中数不能大于三分尝试数");
            }
        }

        // MVP/SVP互斥校验
        if (Boolean.TRUE.equals(playerStat.getIsMvp()) && Boolean.TRUE.equals(playerStat.getIsSvp())) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "球员不能同时获得MVP和SVP");
        }

        // 得分校验（非负数）
        if (playerStat.getScore() != null && playerStat.getScore() < 0) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "球员得分不能为负数");
        }

        // 其他统计数据非负数校验
        validateNonNegative(playerStat.getFgAttempt(), "投篮尝试数");
        validateNonNegative(playerStat.getFgMade(), "投篮命中数");
        validateNonNegative(playerStat.getThreeAttempt(), "三分尝试数");
        validateNonNegative(playerStat.getThreeMade(), "三分命中数");
        validateNonNegative(playerStat.getAssist(), "助攻数");
        validateNonNegative(playerStat.getRebound(), "篮板数");
        validateNonNegative(playerStat.getSteal(), "抢断数");
        validateNonNegative(playerStat.getBlock(), "盖帽数");
        validateNonNegative(playerStat.getTurnover(), "失误数");
        validateNonNegative(playerStat.getDunk(), "灌篮次数");
        validateNonNegative(playerStat.getMidCount(), "中投次数");
    }

    /**
     * 校验跨数据一致性（比赛信息、队伍统计、球员统计之间的一致性）
     */
    private void validateCrossConsistency(MatchGameCreateQuery query) {
        validateCrossConsistencyInternal(
                query.getMyScore(), 
                query.getOppScore(),
                query.getTeamStatsList(), 
                query.getPlayerStatsList(),
                query.getIsRobot()
        );
    }

    /**
     * 校验跨数据一致性（比赛信息、队伍统计、球员统计之间的一致性）
     */
    private void validateCrossConsistency(MatchGameUpdateQuery query) {
        validateCrossConsistencyInternal(
                query.getMyScore(), 
                query.getOppScore(),
                query.getTeamStatsList(), 
                query.getPlayerStatsList(),
                query.getIsRobot()
        );
    }

    /**
     * 校验跨数据一致性核心逻辑
     */
    private void validateCrossConsistencyInternal(
            Integer myScore, 
            Integer oppScore,
            List<MatchTeamStats> teamStatsList, 
            List<MatchPlayerStats> playerStatsList,
            Boolean isRobot) {
        
        // 获取队伍统计数据
        MatchTeamStats myTeamStat = teamStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 1))
                .findFirst()
                .orElse(null);
        MatchTeamStats opponentTeamStat = teamStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 2))
                .findFirst()
                .orElse(null);

        if (myTeamStat == null || opponentTeamStat == null) {
            return; // 基础校验已确保必有数据
        }

        // 1. 比赛信息得分与队伍统计得分一致性校验
        if (myScore != null && myTeamStat.getScore() != null && !Objects.equals(myScore, myTeamStat.getScore())) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "比赛信息中我方得分与队伍统计中我方得分不一致");
        }

        if (oppScore != null && opponentTeamStat.getScore() != null && !Objects.equals(oppScore, opponentTeamStat.getScore())) {
            throw new BizException(BizErrorCode.INVALID_PARAM, "比赛信息中对方得分与队伍统计中对方得分不一致");
        }

        // 2. 队伍统计得分与球员统计总得分一致性校验
        validateTeamScoreWithPlayerScores(myTeamStat, playerStatsList, 1, "我方");
        
        // 非机器人对局时校验对方队伍得分一致性
        if (!Boolean.TRUE.equals(isRobot)) {
            validateTeamScoreWithPlayerScores(opponentTeamStat, playerStatsList, 2, "对方");
        }

        // 3. 队伍统计各项指标与球员统计总和一致性校验
        validateTeamStatsWithPlayerStats(myTeamStat, playerStatsList, 1, "我方");
    }

    /**
     * 校验队伍得分与对应球员得分总和的一致性
     */
    private void validateTeamScoreWithPlayerScores(
            MatchTeamStats teamStat, 
            List<MatchPlayerStats> playerStatsList, 
            Integer teamType, 
            String teamTypeName) {
        
        if (teamStat.getScore() == null) {
            return;
        }

        int playerTotalScore = playerStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), teamType))
                .mapToInt(stat -> stat.getScore() != null ? stat.getScore() : 0)
                .sum();

        if (!Objects.equals(teamStat.getScore(), playerTotalScore)) {
            throw new BizException(BizErrorCode.INVALID_PARAM, teamTypeName + "队伍统计得分与球员得分总和不一致");
        }
    }

    /**
     * 校验队伍统计数据与对应球员统计数据总和的一致性
     */
    private void validateTeamStatsWithPlayerStats(
            MatchTeamStats teamStat, 
            List<MatchPlayerStats> playerStatsList, 
            Integer teamType, 
            String teamTypeName) {
        
        List<MatchPlayerStats> teamPlayers = playerStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), teamType))
                .collect(Collectors.toList());

        // 投篮数据校验
        validateSumConsistency(teamStat.getFgAttempt(), teamPlayers, MatchPlayerStats::getFgAttempt, 
                teamTypeName + "队伍投篮尝试数与球员投篮尝试数总和不一致");
        validateSumConsistency(teamStat.getFgMade(), teamPlayers, MatchPlayerStats::getFgMade, 
                teamTypeName + "队伍投篮命中数与球员投篮命中数总和不一致");

        // 三分数据校验
        validateSumConsistency(teamStat.getThreeAttempt(), teamPlayers, MatchPlayerStats::getThreeAttempt, 
                teamTypeName + "队伍三分尝试数与球员三分尝试数总和不一致");
        validateSumConsistency(teamStat.getThreeMade(), teamPlayers, MatchPlayerStats::getThreeMade, 
                teamTypeName + "队伍三分命中数与球员三分命中数总和不一致");

        // 基础统计数据校验
        validateSumConsistency(teamStat.getAssist(), teamPlayers, MatchPlayerStats::getAssist, 
                teamTypeName + "队伍助攻数与球员助攻数总和不一致");
        validateSumConsistency(teamStat.getRebound(), teamPlayers, MatchPlayerStats::getRebound, 
                teamTypeName + "队伍篮板数与球员篮板数总和不一致");
        validateSumConsistency(teamStat.getSteal(), teamPlayers, MatchPlayerStats::getSteal, 
                teamTypeName + "队伍抢断数与球员抢断数总和不一致");
        validateSumConsistency(teamStat.getBlock(), teamPlayers, MatchPlayerStats::getBlock, 
                teamTypeName + "队伍盖帽数与球员盖帽数总和不一致");
        validateSumConsistency(teamStat.getDunk(), teamPlayers, MatchPlayerStats::getDunk, 
                teamTypeName + "队伍灌篮次数与球员灌篮次数总和不一致");
    }

    /**
     * 校验总和一致性
     */
    private void validateSumConsistency(
            Integer teamValue, 
            List<MatchPlayerStats> playerStatsList, 
            java.util.function.Function<MatchPlayerStats, Integer> valueExtractor, 
            String errorMessage) {
        
        if (teamValue == null) {
            return;
        }

        int playerTotal = playerStatsList.stream()
                .mapToInt(stat -> {
                    Integer value = valueExtractor.apply(stat);
                    return value != null ? value : 0;
                })
                .sum();

        if (!Objects.equals(teamValue, playerTotal)) {
            throw new BizException(BizErrorCode.INVALID_PARAM, errorMessage);
        }
    }

    /**
     * 校验数值非负性
     */
    private void validateNonNegative(Integer value, String fieldName) {
        if (value != null && value < 0) {
            throw new BizException(BizErrorCode.INVALID_PARAM, fieldName + "不能为负数");
        }
    }
}