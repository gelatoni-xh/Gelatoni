package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameBaseDataDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsMetric;
import com.csxuhuan.gelatoni.application.dto.OpponentStatsDTO;
import com.csxuhuan.gelatoni.application.service.MatchGameAppService;
import com.csxuhuan.gelatoni.application.service.MatchGameStatsCalculator;
import com.csxuhuan.gelatoni.application.service.MatchGameDataValidator;
import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.domain.service.MatchGameDomainService;
import com.csxuhuan.gelatoni.application.assembler.MatchGameAssembler;
import com.csxuhuan.gelatoni.infrastructure.redis.generator.MatchGameStatsCacheKeyGenerator;
import com.csxuhuan.gelatoni.infrastructure.redis.manager.MatchGameStatsCacheManager;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchGameRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchPlayerStatsRepository;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameCreateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameUpdateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * 比赛应用服务实现类
 *
 * @author Gelatoni
 */
@Service
public class MatchGameAppServiceImpl implements MatchGameAppService {

    private final MatchGameDomainService matchGameDomainService;
    private final MatchGameRepository matchGameRepository;
    private final MatchPlayerStatsRepository matchPlayerStatsRepository;
    private final MatchGameDataValidator dataValidator;
    private final MatchGameStatsCacheManager cacheManager;
    private final MatchGameStatsCacheKeyGenerator keyGenerator;
    private final MatchGameAssembler assembler = new MatchGameAssembler();

    public MatchGameAppServiceImpl(MatchGameDomainService matchGameDomainService,
                                  MatchGameRepository matchGameRepository,
                                  MatchPlayerStatsRepository matchPlayerStatsRepository,
                                  MatchGameDataValidator dataValidator,
                                  MatchGameStatsCacheManager cacheManager,
                                  MatchGameStatsCacheKeyGenerator keyGenerator) {
        this.matchGameDomainService = matchGameDomainService;
        this.matchGameRepository = matchGameRepository;
        this.matchPlayerStatsRepository = matchPlayerStatsRepository;
        this.dataValidator = dataValidator;
        this.cacheManager = cacheManager;
        this.keyGenerator = keyGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long createMatchGame(MatchGameCreateQuery query) {
        // 数据校验
        dataValidator.validateCreateData(query);
        
        return matchGameDomainService.createMatchGame(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMatchGame(MatchGameUpdateQuery query) {
        // 数据校验
        dataValidator.validateUpdateData(query);
        
        return matchGameDomainService.updateMatchGame(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteMatchGame(Long id) {
        return matchGameDomainService.deleteMatchGame(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchGameDTO> getMatchGamePage(MatchGamePageQuery query) {
        List<MatchGame> matchGames = matchGameDomainService.getMatchGamePage(query);
        return assembler.toMatchGameDTOList(matchGames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchGameDetailDTO getMatchGameDetail(Long id) {
        MatchGameDomainService.MatchGameDetailData detailData = matchGameDomainService.getMatchGameDetail(id);

        if (detailData == null) {
            return null;
        }

        // 转换各种数据为DTO格式
        MatchGameDTO matchGameDTO = assembler.toDTO(detailData.getMatchGame());
        List<com.csxuhuan.gelatoni.application.dto.MatchTeamStatsDTO> myTeamStatsDTOs = 
            assembler.toMatchTeamStatsDTOList(detailData.getMyTeamStats());
        List<com.csxuhuan.gelatoni.application.dto.MatchTeamStatsDTO> opponentTeamStatsDTOs = 
            assembler.toMatchTeamStatsDTOList(detailData.getOpponentTeamStats());
        List<com.csxuhuan.gelatoni.application.dto.MatchPlayerStatsDTO> myPlayerStatsDTOs = 
            assembler.toMatchPlayerStatsDTOList(detailData.getMyPlayerStats());
        List<com.csxuhuan.gelatoni.application.dto.MatchPlayerStatsDTO> opponentPlayerStatsDTOs = 
            assembler.toMatchPlayerStatsDTOList(detailData.getOpponentPlayerStats());

        return assembler.toDetailDTO(matchGameDTO, myTeamStatsDTOs, opponentTeamStatsDTOs, 
                myPlayerStatsDTOs, opponentPlayerStatsDTOs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchGameStatsDTO getMatchGameStats(MatchGameStatsRequest request) {
        // 约束：只统计我方数据（team_type=1），由 Repository/SQL 保证。

        // 1. 生成缓存键
        String cacheKey = keyGenerator.generateKey(request);
        
        // 2. 尝试从缓存获取
        MatchGameStatsDTO cachedStats = cacheManager.getStats(cacheKey);
        if (cachedStats != null) {
            return cachedStats;
        }

        // 3. 缓存未命中，执行数据库查询和计算
        String season = request == null ? null : request.getSeason();
        Boolean excludeRobot = request == null ? null : request.getExcludeRobot();
        String matchDate = request == null ? null : request.getMatchDate();
        MatchGameStatsRequest.StatsDimension reqDim = request == null ? null : request.getDimension();

        MatchGameStatsDTO.Dimension dim = reqDim == MatchGameStatsRequest.StatsDimension.USER
                ? MatchGameStatsDTO.Dimension.USER
                : MatchGameStatsDTO.Dimension.PLAYER;

        List<MatchPlayerStats> myPlayerStats = matchPlayerStatsRepository.findMyPlayerStatsForStats(season, excludeRobot, matchDate);
        MatchGameStatsDTO calculatedStats = MatchGameStatsCalculator.calculate(season, dim, myPlayerStats);

        // 4. 将计算结果存入缓存
        cacheManager.setStats(cacheKey, calculatedStats);

        return calculatedStats;
    }

    @Override
    public OpponentStatsDTO getOpponentStats(String season) {
        // 查询对方球员数据（team_type=2），排除机器人
        List<MatchPlayerStats> opponentStats = matchPlayerStatsRepository.findOpponentPlayerStatsForStats(season, true);
        
        // 获取所有比赛ID并查询结果
        java.util.Set<Long> matchIds = opponentStats.stream()
                .map(MatchPlayerStats::getMatchId)
                .collect(java.util.stream.Collectors.toSet());
        Map<Long, Boolean> matchResults = new java.util.HashMap<>();
        if (!matchIds.isEmpty()) {
            for (Long matchId : matchIds) {
                MatchGame match = matchGameRepository.findById(matchId);
                if (match != null) {
                    matchResults.put(matchId, match.getResult());
                }
            }
        }
        
        // 按对手球员名称分组统计
        Map<String, List<MatchPlayerStats>> groupedByPlayer = opponentStats.stream()
                .collect(Collectors.groupingBy(MatchPlayerStats::getPlayerName));
        
        List<OpponentStatsDTO.OpponentRecord> records = groupedByPlayer.entrySet().stream()
                .map(entry -> {
                    String playerName = entry.getKey();
                    List<MatchPlayerStats> playerMatches = entry.getValue();
                    
                    int totalGames = playerMatches.size();
                    int wins = (int) playerMatches.stream()
                            .filter(p -> matchResults.getOrDefault(p.getMatchId(), false))
                            .count();
                    int losses = totalGames - wins;
                    double winRate = totalGames > 0 ? (double) wins / totalGames : 0;
                    
                    // 难度等级：0=新手(1-2场), 1=散点(3-5场), 2=常客(6-10场), 3=老对手(11+场)
                    int difficulty = totalGames <= 2 ? 0 : (totalGames <= 5 ? 1 : (totalGames <= 10 ? 2 : 3));
                    
                    return new OpponentStatsDTO.OpponentRecord(playerName, totalGames, wins, losses, winRate, difficulty);
                })
                .sorted((a, b) -> {
                    // 按胜率升序排列（最难的对手在前）
                    int cmp = Double.compare(a.getWinRate(), b.getWinRate());
                    return cmp != 0 ? cmp : Integer.compare(b.getTotalGames(), a.getTotalGames());
                })
                .collect(Collectors.toList());
        
        // 计算摘要
        int maxLosses = records.stream().mapToInt(OpponentStatsDTO.OpponentRecord::getLosses).max().orElse(0);
        double avgWinRate = records.isEmpty() ? 0 : records.stream()
                .mapToDouble(OpponentStatsDTO.OpponentRecord::getWinRate)
                .average()
                .orElse(0);
        
        OpponentStatsDTO.Summary summary = new OpponentStatsDTO.Summary(records.size(), maxLosses, avgWinRate);
        
        return new OpponentStatsDTO(season, records, summary);
    }

    @Override
    public MatchGameBaseDataDTO getMatchGameBaseData() {
        MatchGameBaseDataDTO dto = new MatchGameBaseDataDTO();
        dto.setSeasons(matchGameRepository.findDistinctSeasons());
        dto.setMyPlayerNames(matchPlayerStatsRepository.findDistinctPlayerNames(1));
        dto.setOpponentPlayerNames(matchPlayerStatsRepository.findDistinctPlayerNames(2));
        dto.setMyUserNames(matchPlayerStatsRepository.findDistinctMyUserNames());
        dto.setMatchDatesBySeason(matchGameRepository.findMatchDatesBySeason());
        dto.setMetricConfigs(MatchGameStatsMetric.getAllMetricConfigs());
        return dto;
    }

    @Override
    public void validateCreateRequest(MatchGameCreateRequest request) {
        MatchGameCreateQuery query = assembler.toDomainQuery(request);
        dataValidator.validateCreateData(query);
    }

    @Override
    public void validateUpdateRequest(MatchGameUpdateRequest request) {
        MatchGameUpdateQuery query = assembler.toDomainQuery(request);
        dataValidator.validateUpdateData(query);
    }
}