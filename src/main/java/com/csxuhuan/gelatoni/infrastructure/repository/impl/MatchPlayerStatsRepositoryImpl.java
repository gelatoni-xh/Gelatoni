package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.model.converter.MatchPlayerStatsConverter;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchPlayerStatsRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchPlayerStatsDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.MatchPlayerStatsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛球员统计仓储实现类
 *
 * @author Gelatoni
 */
@Repository
public class MatchPlayerStatsRepositoryImpl implements MatchPlayerStatsRepository {

    private final MatchPlayerStatsMapper matchPlayerStatsMapper;

    public MatchPlayerStatsRepositoryImpl(MatchPlayerStatsMapper matchPlayerStatsMapper) {
        this.matchPlayerStatsMapper = matchPlayerStatsMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchPlayerStats> findByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByAsc(MatchPlayerStatsDO::getTeamType);
        List<MatchPlayerStatsDO> matchPlayerStatsDOList = matchPlayerStatsMapper.selectList(wrapper);
        return matchPlayerStatsDOList.stream()
                .map(MatchPlayerStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchPlayerStats> findMyPlayerStatsByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getTeamType, 1) // 1=我方
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<MatchPlayerStatsDO> matchPlayerStatsDOList = matchPlayerStatsMapper.selectList(wrapper);
        return matchPlayerStatsDOList.stream()
                .map(MatchPlayerStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchPlayerStats> findOpponentPlayerStatsByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getTeamType, 2) // 2=对方
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<MatchPlayerStatsDO> matchPlayerStatsDOList = matchPlayerStatsMapper.selectList(wrapper);
        return matchPlayerStatsDOList.stream()
                .map(MatchPlayerStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int batchCreate(List<MatchPlayerStats> playerStatsList, Long creator) {
        if (playerStatsList == null || playerStatsList.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (MatchPlayerStats playerStat : playerStatsList) {
            MatchPlayerStatsDO playerStatDO = MatchPlayerStatsConverter.toDO(playerStat);
            playerStatDO.setCreator(creator);
            playerStatDO.setModifier(creator);
            count += matchPlayerStatsMapper.insert(playerStatDO);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int batchUpdate(List<MatchPlayerStats> playerStatsList, Long modifier) {
        if (playerStatsList == null || playerStatsList.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (MatchPlayerStats playerStat : playerStatsList) {
            MatchPlayerStatsDO playerStatDO = new MatchPlayerStatsDO();
            playerStatDO.setId(playerStat.getId());
            playerStatDO.setMatchId(playerStat.getMatchId());
            playerStatDO.setTeamType(playerStat.getTeamType());
            playerStatDO.setUserName(playerStat.getUserName());
            playerStatDO.setPlayerName(playerStat.getPlayerName());
            playerStatDO.setRating(playerStat.getRating());
            playerStatDO.setIsMvp(playerStat.getIsMvp());
            playerStatDO.setIsSvp(playerStat.getIsSvp());
            playerStatDO.setScore(playerStat.getScore());
            playerStatDO.setAssist(playerStat.getAssist());
            playerStatDO.setRebound(playerStat.getRebound());
            playerStatDO.setSteal(playerStat.getSteal());
            playerStatDO.setBlock(playerStat.getBlock());
            playerStatDO.setTurnover(playerStat.getTurnover());
            playerStatDO.setDunk(playerStat.getDunk());
            playerStatDO.setFgAttempt(playerStat.getFgAttempt());
            playerStatDO.setFgMade(playerStat.getFgMade());
            playerStatDO.setThreeAttempt(playerStat.getThreeAttempt());
            playerStatDO.setThreeMade(playerStat.getThreeMade());
            playerStatDO.setMidCount(playerStat.getMidCount());
            playerStatDO.setMaxScoringRun(playerStat.getMaxScoringRun());
            playerStatDO.setModifier(modifier);

            LambdaUpdateWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(MatchPlayerStatsDO::getId, playerStat.getId())
                    .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

            count += matchPlayerStatsMapper.update(playerStatDO, wrapper);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByMatchId(Long matchId, Long modifier) {
        LambdaUpdateWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchPlayerStatsDO playerStatDO = new MatchPlayerStatsDO();
        playerStatDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        playerStatDO.setModifier(modifier);

        return matchPlayerStatsMapper.update(playerStatDO, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByMatchIdAndTeamType(Long matchId, Integer teamType, Long modifier) {
        LambdaUpdateWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getTeamType, teamType)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchPlayerStatsDO playerStatDO = new MatchPlayerStatsDO();
        playerStatDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        playerStatDO.setModifier(modifier);

        return matchPlayerStatsMapper.update(playerStatDO, wrapper);
    }
}