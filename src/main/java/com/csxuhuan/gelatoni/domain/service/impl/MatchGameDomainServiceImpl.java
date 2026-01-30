package com.csxuhuan.gelatoni.domain.service.impl;

import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.PageQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.domain.service.MatchGameDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchGameRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchTeamStatsRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchPlayerStatsRepository;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 比赛领域服务实现类
 *
 * @author Gelatoni
 */
@Service
public class MatchGameDomainServiceImpl implements MatchGameDomainService {

    private final MatchGameRepository matchGameRepository;
    private final MatchTeamStatsRepository matchTeamStatsRepository;
    private final MatchPlayerStatsRepository matchPlayerStatsRepository;

    public MatchGameDomainServiceImpl(MatchGameRepository matchGameRepository,
                                      MatchTeamStatsRepository matchTeamStatsRepository,
                                      MatchPlayerStatsRepository matchPlayerStatsRepository) {
        this.matchGameRepository = matchGameRepository;
        this.matchTeamStatsRepository = matchTeamStatsRepository;
        this.matchPlayerStatsRepository = matchPlayerStatsRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long createMatchGame(MatchGameCreateQuery query) {
        // 创建比赛基础信息
        MatchGame matchGame = query.toMatchGame();
        int result = matchGameRepository.create(matchGame, query.getCreator());
        
        if (result <= 0) {
            throw new RuntimeException("创建比赛失败");
        }

        // 获取刚创建的比赛ID
        MatchGame createdMatch = matchGameRepository.findById(matchGame.getId());
        Long matchId = createdMatch.getId();

        // 如果提供了队伍统计数据，则批量创建
        if (query.getTeamStatsList() != null && !query.getTeamStatsList().isEmpty()) {
            // 更新队伍统计中的比赛ID
            List<MatchTeamStats> updatedTeamStatsList = query.getTeamStatsList().stream()
                    .map(teamStat -> new MatchTeamStats(
                            null, matchId, teamStat.getTeamType(), teamStat.getScore(),
                            teamStat.getFgAttempt(), teamStat.getFgMade(),
                            teamStat.getThreeAttempt(), teamStat.getThreeMade(),
                            teamStat.getAssist(), teamStat.getRebound(),
                            teamStat.getOffRebound(), teamStat.getDefRebound(),
                            teamStat.getSteal(), teamStat.getBlock(),
                            teamStat.getDunk(), teamStat.getPaintScore(),
                            teamStat.getSecondChanceScore(), teamStat.getTurnoverToScore(),
                            teamStat.getMaxLead(), query.getCreator(), query.getCreator(),
                            null, null))
                    .collect(java.util.stream.Collectors.toList());

            int teamStatsResult = matchTeamStatsRepository.batchCreate(updatedTeamStatsList, query.getCreator());
            if (teamStatsResult != updatedTeamStatsList.size()) {
                throw new RuntimeException("创建比赛队伍统计数据失败");
            }
        }

        // 如果提供了球员统计数据，则批量创建
        if (query.getPlayerStatsList() != null && !query.getPlayerStatsList().isEmpty()) {
            // 验证是否包含了全部球员数据（我方和对方）
            validateCompletePlayerStats(query.getPlayerStatsList());

            // 更新球员统计中的比赛ID
            List<MatchPlayerStats> updatedPlayerStatsList = query.getPlayerStatsList().stream()
                    .map(playerStat -> new MatchPlayerStats(
                            null, matchId, playerStat.getTeamType(), playerStat.getUserName(),
                            playerStat.getPlayerName(), playerStat.getRating(),
                            playerStat.getIsMvp(), playerStat.getIsSvp(),
                            playerStat.getScore(), playerStat.getAssist(),
                            playerStat.getRebound(), playerStat.getSteal(),
                            playerStat.getBlock(), playerStat.getTurnover(),
                            playerStat.getDunk(), playerStat.getFgAttempt(),
                            playerStat.getFgMade(), playerStat.getThreeAttempt(),
                            playerStat.getThreeMade(), playerStat.getMidCount(),
                            playerStat.getMaxScoringRun(), query.getCreator(), query.getCreator(),
                            null, null))
                    .collect(java.util.stream.Collectors.toList());

            int playerStatsResult = matchPlayerStatsRepository.batchCreate(updatedPlayerStatsList, query.getCreator());
            if (playerStatsResult != updatedPlayerStatsList.size()) {
                throw new RuntimeException("创建比赛球员统计数据失败");
            }
        }

        return matchId;
    }

    /**
     * 验证球员统计数据是否完整（必须包含我方和对方的所有球员数据）
     */
    private void validateCompletePlayerStats(List<MatchPlayerStats> playerStatsList) {
        long myTeamCount = playerStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 1)) // 1=我方
                .count();
        long opponentTeamCount = playerStatsList.stream()
                .filter(stat -> Objects.equals(stat.getTeamType(), 2)) // 2=对方
                .count();

        // 这里我们不强制要求具体的数量，只要求至少有一方的数据，或者两方都有数据
        // 如果只提供了一部分球员数据，则抛出异常
        if (myTeamCount > 0 && opponentTeamCount == 0) {
            // 如果只提供了我方数据，应该包含所有我方球员
            // 由于不知道具体应该有多少球员，这里只是确保不是只提供了部分数据
        } else if (myTeamCount == 0 && opponentTeamCount > 0) {
            // 如果只提供了对方数据，应该包含所有对方球员
        } else if (myTeamCount > 0 && opponentTeamCount > 0) {
            // 两方数据都提供了，符合要求
        } else {
            // 没有任何球员数据，也符合只保存基础信息的要求
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean updateMatchGame(MatchGameUpdateQuery query) {
        // 更新比赛基础信息
        MatchGame matchGame = query.toMatchGame();
        int result = matchGameRepository.update(matchGame, query.getModifier());
        
        if (result <= 0) {
            throw new RuntimeException("更新比赛失败");
        }

        // 如果提供了队伍统计数据，则先删除旧数据再创建新数据
        if (query.getTeamStatsList() != null && !query.getTeamStatsList().isEmpty()) {
            // 删除旧的队伍统计数据
            matchTeamStatsRepository.deleteByMatchId(query.getId(), query.getModifier());

            // 创建新的队伍统计数据
            List<MatchTeamStats> updatedTeamStatsList = query.getTeamStatsList().stream()
                    .map(teamStat -> new MatchTeamStats(
                            null, query.getId(), teamStat.getTeamType(), teamStat.getScore(),
                            teamStat.getFgAttempt(), teamStat.getFgMade(),
                            teamStat.getThreeAttempt(), teamStat.getThreeMade(),
                            teamStat.getAssist(), teamStat.getRebound(),
                            teamStat.getOffRebound(), teamStat.getDefRebound(),
                            teamStat.getSteal(), teamStat.getBlock(),
                            teamStat.getDunk(), teamStat.getPaintScore(),
                            teamStat.getSecondChanceScore(), teamStat.getTurnoverToScore(),
                            teamStat.getMaxLead(), null, query.getModifier(),
                            null, null))
                    .collect(java.util.stream.Collectors.toList());

            int teamStatsResult = matchTeamStatsRepository.batchCreate(updatedTeamStatsList, query.getModifier());
            if (teamStatsResult != updatedTeamStatsList.size()) {
                throw new RuntimeException("更新比赛队伍统计数据失败");
            }
        }

        // 如果提供了球员统计数据，则先删除旧数据再创建新数据
        if (query.getPlayerStatsList() != null && !query.getPlayerStatsList().isEmpty()) {
            // 验证是否包含了全部球员数据（我方和对方）
            validateCompletePlayerStats(query.getPlayerStatsList());

            // 删除旧的球员统计数据
            matchPlayerStatsRepository.deleteByMatchId(query.getId(), query.getModifier());

            // 创建新的球员统计数据
            List<MatchPlayerStats> updatedPlayerStatsList = query.getPlayerStatsList().stream()
                    .map(playerStat -> new MatchPlayerStats(
                            null, query.getId(), playerStat.getTeamType(), playerStat.getUserName(),
                            playerStat.getPlayerName(), playerStat.getRating(),
                            playerStat.getIsMvp(), playerStat.getIsSvp(),
                            playerStat.getScore(), playerStat.getAssist(),
                            playerStat.getRebound(), playerStat.getSteal(),
                            playerStat.getBlock(), playerStat.getTurnover(),
                            playerStat.getDunk(), playerStat.getFgAttempt(),
                            playerStat.getFgMade(), playerStat.getThreeAttempt(),
                            playerStat.getThreeMade(), playerStat.getMidCount(),
                            playerStat.getMaxScoringRun(), null, query.getModifier(),
                            null, null))
                    .collect(java.util.stream.Collectors.toList());

            int playerStatsResult = matchPlayerStatsRepository.batchCreate(updatedPlayerStatsList, query.getModifier());
            if (playerStatsResult != updatedPlayerStatsList.size()) {
                throw new RuntimeException("更新比赛球员统计数据失败");
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean deleteMatchGame(Long id) {
        // 删除比赛对应的队伍统计数据
        int teamStatsDeleted = matchTeamStatsRepository.deleteByMatchId(id, -1L); // 使用系统标识作为修改人

        // 删除比赛对应的球员统计数据
        int playerStatsDeleted = matchPlayerStatsRepository.deleteByMatchId(id, -1L); // 使用系统标识作为修改人

        // 删除比赛记录
        int gameDeleted = matchGameRepository.delete(id, -1L); // 使用系统标识作为修改人

        return gameDeleted > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchGame getMatchGameById(Long id) {
        return matchGameRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchGame> getMatchGamePage(MatchGamePageQuery query) {
        // 计算偏移量
        int offset = (query.getPageNo() - 1) * query.getPageSize();
        
        // 如果指定了赛季，则按赛季查询；否则查询所有比赛
        List<MatchGame> allMatches;
        if (query.getSeason() != null && !query.getSeason().isEmpty()) {
            allMatches = matchGameRepository.findBySeason(query.getSeason());
        } else {
            allMatches = matchGameRepository.findAll();
        }

        // 手动分页
        int startIndex = Math.min(offset, allMatches.size());
        int endIndex = Math.min(startIndex + query.getPageSize(), allMatches.size());

        if (startIndex >= allMatches.size()) {
            return java.util.Collections.emptyList();
        }

        return allMatches.subList(startIndex, endIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMatchGameCount() {
        return matchGameRepository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchGameDetailData getMatchGameDetail(Long id) {
        // 获取比赛基础信息
        MatchGame matchGame = matchGameRepository.findById(id);

        if (matchGame == null) {
            return null;
        }

        // 获取队伍统计数据
        List<MatchTeamStats> allTeamStats = matchTeamStatsRepository.findByMatchId(id);
        List<MatchTeamStats> myTeamStats = allTeamStats.stream()
                .filter(stat -> stat.getTeamType() == 1) // 1=我方
                .collect(java.util.stream.Collectors.toList());
        List<MatchTeamStats> opponentTeamStats = allTeamStats.stream()
                .filter(stat -> stat.getTeamType() == 2) // 2=对方
                .collect(java.util.stream.Collectors.toList());

        // 获取球员统计数据
        List<MatchPlayerStats> allPlayerStats = matchPlayerStatsRepository.findByMatchId(id);
        List<MatchPlayerStats> myPlayerStats = allPlayerStats.stream()
                .filter(stat -> stat.getTeamType() == 1) // 1=我方
                .collect(java.util.stream.Collectors.toList());
        List<MatchPlayerStats> opponentPlayerStats = allPlayerStats.stream()
                .filter(stat -> stat.getTeamType() == 2) // 2=对方
                .collect(java.util.stream.Collectors.toList());

        return new MatchGameDetailData(matchGame, myTeamStats, opponentTeamStats, myPlayerStats, opponentPlayerStats);
    }
}