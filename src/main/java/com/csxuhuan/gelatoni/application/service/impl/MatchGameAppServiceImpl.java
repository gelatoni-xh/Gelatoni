package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameBaseDataDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsMetric;
import com.csxuhuan.gelatoni.application.dto.MatchGameTrendDTO;
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
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameTrendRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public MatchGameTrendDTO getMatchGameTrend(MatchGameTrendRequest request) {
    	String season = request == null ? null : request.getSeason();
    	Boolean excludeRobot = request == null ? null : request.getExcludeRobot();
       
    	// 获取所有数据
    	List<MatchPlayerStats> allPlayerStats = matchPlayerStatsRepository.findMyPlayerStatsForStats(season, excludeRobot, null);
    	
    	// 按游戏日期分组（游戏日期范围：8:00-次日2:00）
    	Map<String, List<MatchPlayerStats>> groupedByDate = new HashMap<>();
    	Map<String, Set<Long>> dateToMatchIds = new HashMap<>();
    	
    	for (MatchPlayerStats stat : allPlayerStats) {
    		MatchGame match = matchGameRepository.findById(stat.getMatchId());
    		if (match != null && match.getMatchTime() != null) {
    			String dateStr = getGameDate(match.getMatchTime());
    			groupedByDate.computeIfAbsent(dateStr, k -> new ArrayList<>()).add(stat);
    			dateToMatchIds.computeIfAbsent(dateStr, k -> new java.util.HashSet<>()).add(stat.getMatchId());
    		}
    	}
    	
    	List<String> sortedDates = groupedByDate.keySet().stream().sorted().collect(Collectors.toList());
    	
    	// 计算每日胜率
    	List<Double> winRates = new ArrayList<>();
    	for (String date : sortedDates) {
    		Set<Long> matchIds = dateToMatchIds.get(date);
    		if (matchIds == null || matchIds.isEmpty()) {
    			continue;
    		}
    		int wins = (int) matchIds.stream()
    				.map(matchGameRepository::findById)
    				.filter(m -> m != null && Boolean.TRUE.equals(m.getResult()))
    				.count();
    		double winRate = (double) wins / matchIds.size();
    		winRates.add(Math.round(winRate * 1000.0) / 1000.0);
    	}
    	
    	// 过滤掉没有数据的日期
    	List<String> validDates = new ArrayList<>();
    	for (String date : sortedDates) {
    		if (dateToMatchIds.get(date) != null && !dateToMatchIds.get(date).isEmpty()) {
    			validDates.add(date);
    		}
    	}
    	sortedDates = validDates;
    	
    	// 按日期+球员维度累加各指标，计算场均
    	Map<String, Map<String, List<Double>>> playerMetrics = new LinkedHashMap<>();
    	Set<String> allPlayers = new java.util.HashSet<>();
    	for (List<MatchPlayerStats> dayStats : groupedByDate.values()) {
    		dayStats.forEach(s -> allPlayers.add(s.getPlayerName()));
    	}
    	
    	for (String playerName : allPlayers) {
    		Map<String, List<Double>> metrics = new LinkedHashMap<>();
    		List<Double> ratings = new ArrayList<>();
    		List<Double> scores = new ArrayList<>();
    		List<Double> rebounds = new ArrayList<>();
    		List<Double> assists = new ArrayList<>();
    		List<Double> steals = new ArrayList<>();
    		List<Double> blocks = new ArrayList<>();
    		
    		for (String date : sortedDates) {
    			List<MatchPlayerStats> dayStats = groupedByDate.get(date);
    			// 按日期+球员维度累加各指标
    			double ratingSum = 0;
    			int scoreSum = 0;
    			int reboundSum = 0;
    			int assistSum = 0;
    			int stealSum = 0;
    			int blockSum = 0;
    			int gameCount = 0;
    			
    			for (MatchPlayerStats stat : dayStats) {
    				if (playerName.equals(stat.getPlayerName())) {
    					ratingSum += stat.getRating() != null ? stat.getRating() : 0;
    					scoreSum += stat.getScore() != null ? stat.getScore() : 0;
    					reboundSum += stat.getRebound() != null ? stat.getRebound() : 0;
    					assistSum += stat.getAssist() != null ? stat.getAssist() : 0;
    					stealSum += stat.getSteal() != null ? stat.getSteal() : 0;
    					blockSum += stat.getBlock() != null ? stat.getBlock() : 0;
    					gameCount++;
    				}
    			}
    			
    			// 计算场均
    			if (gameCount > 0) {
    				ratings.add(Math.round(ratingSum / gameCount * 10.0) / 10.0);
    				scores.add(Math.round((double) scoreSum / gameCount * 10.0) / 10.0);
    				rebounds.add(Math.round((double) reboundSum / gameCount * 10.0) / 10.0);
    				assists.add(Math.round((double) assistSum / gameCount * 10.0) / 10.0);
    				steals.add(Math.round((double) stealSum / gameCount * 10.0) / 10.0);
    				blocks.add(Math.round((double) blockSum / gameCount * 10.0) / 10.0);
    			} else {
    				ratings.add(0.0);
    				scores.add(0.0);
    				rebounds.add(0.0);
    				assists.add(0.0);
    				steals.add(0.0);
    				blocks.add(0.0);
    			}
    		}
    		
    		metrics.put("rating", ratings);
    		metrics.put("score", scores);
    		metrics.put("rebound", rebounds);
    		metrics.put("assist", assists);
    		metrics.put("steal", steals);
    		metrics.put("block", blocks);
    		playerMetrics.put(playerName, metrics);
    	}
    	
    	return new MatchGameTrendDTO(sortedDates, winRates, playerMetrics);
    }

    @Override
    public OpponentStatsDTO getOpponentStats(String season, Integer minGames) {
        final int minGamesValue = minGames != null ? minGames : 3;
        
        // 查询对方球员数据（team_type=2），排除机器人
        List<MatchPlayerStats> opponentStats = matchPlayerStatsRepository.findOpponentPlayerStatsForStats(season, true);
        
        // 获取所有比赛ID并查询结果和得分
        java.util.Set<Long> matchIds = opponentStats.stream()
                .map(MatchPlayerStats::getMatchId)
                .collect(java.util.stream.Collectors.toSet());
        Map<Long, MatchGame> matchMap = new java.util.HashMap<>();
        if (!matchIds.isEmpty()) {
            for (Long matchId : matchIds) {
                MatchGame match = matchGameRepository.findById(matchId);
                if (match != null) {
                    matchMap.put(matchId, match);
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
                            .filter(p -> {
                                MatchGame match = matchMap.get(p.getMatchId());
                                return match != null && match.getResult();
                            })
                            .count();
                    int losses = totalGames - wins;
                    double winRate = totalGames > 0 ? (double) wins / totalGames : 0;
                    
                    // 计算场均净胜分
                    int totalPointDifferential = playerMatches.stream()
                            .mapToInt(p -> {
                                MatchGame match = matchMap.get(p.getMatchId());
                                if (match == null) return 0;
                                return match.getMyScore() - match.getOppScore();
                            })
                            .sum();
                    double avgPointDifferential = totalGames > 0 ? (double) totalPointDifferential / totalGames : 0;
                    
                    return new OpponentStatsDTO.OpponentRecord(playerName, totalGames, wins, losses, winRate, avgPointDifferential);
                })
                .filter(r -> r.getTotalGames() >= minGamesValue)
                .sorted((a, b) -> {
                    // 按胜率升序排列（最难的对手在前）
                    int cmp = Double.compare(a.getWinRate(), b.getWinRate());
                    // 胜率相同时，按场均净胜分升序排列（越低越难对战）
                    return cmp != 0 ? cmp : Double.compare(a.getAvgPointDifferential(), b.getAvgPointDifferential());
                })
                .collect(Collectors.toList());
        
        return new OpponentStatsDTO(season, records);
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

    private String getGameDate(java.time.LocalDateTime matchTime) {
        java.time.LocalDate date = matchTime.toLocalDate();
        java.time.LocalTime timeOfDay = matchTime.toLocalTime();
        // 游戏日期范围：8:00-次日2:00。0:00-8:00 的比赛属于前一天
        if (timeOfDay.isBefore(java.time.LocalTime.of(8, 0))) {
            date = date.minusDays(1);
        }
        return date.toString();
    }
}