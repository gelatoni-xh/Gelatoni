package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.converter.MatchTeamStatsConverter;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchTeamStatsRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchTeamStatsDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.MatchTeamStatsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛队伍统计仓储实现类
 *
 * @author Gelatoni
 */
@Repository
public class MatchTeamStatsRepositoryImpl implements MatchTeamStatsRepository {

    private final MatchTeamStatsMapper matchTeamStatsMapper;

    public MatchTeamStatsRepositoryImpl(MatchTeamStatsMapper matchTeamStatsMapper) {
        this.matchTeamStatsMapper = matchTeamStatsMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchTeamStats> findByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchTeamStatsDO::getMatchId, matchId)
                .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByAsc(MatchTeamStatsDO::getTeamType);
        List<MatchTeamStatsDO> matchTeamStatsDOList = matchTeamStatsMapper.selectList(wrapper);
        return matchTeamStatsDOList.stream()
                .map(MatchTeamStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchTeamStats> findMyTeamStatsByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchTeamStatsDO::getMatchId, matchId)
                .eq(MatchTeamStatsDO::getTeamType, 1) // 1=我方
                .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<MatchTeamStatsDO> matchTeamStatsDOList = matchTeamStatsMapper.selectList(wrapper);
        return matchTeamStatsDOList.stream()
                .map(MatchTeamStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchTeamStats> findOpponentTeamStatsByMatchId(Long matchId) {
        LambdaQueryWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchTeamStatsDO::getMatchId, matchId)
                .eq(MatchTeamStatsDO::getTeamType, 2) // 2=对方
                .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        List<MatchTeamStatsDO> matchTeamStatsDOList = matchTeamStatsMapper.selectList(wrapper);
        return matchTeamStatsDOList.stream()
                .map(MatchTeamStatsConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int batchCreate(List<MatchTeamStats> teamStatsList, Long creator) {
        if (teamStatsList == null || teamStatsList.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (MatchTeamStats teamStat : teamStatsList) {
            MatchTeamStatsDO teamStatDO = MatchTeamStatsConverter.toDO(teamStat);
            teamStatDO.setCreator(creator);
            teamStatDO.setModifier(creator);
            count += matchTeamStatsMapper.insert(teamStatDO);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int batchUpdate(List<MatchTeamStats> teamStatsList, Long modifier) {
        if (teamStatsList == null || teamStatsList.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (MatchTeamStats teamStat : teamStatsList) {
            MatchTeamStatsDO teamStatDO = new MatchTeamStatsDO();
            teamStatDO.setId(teamStat.getId());
            teamStatDO.setMatchId(teamStat.getMatchId());
            teamStatDO.setTeamType(teamStat.getTeamType());
            teamStatDO.setScore(teamStat.getScore());
            teamStatDO.setFgAttempt(teamStat.getFgAttempt());
            teamStatDO.setFgMade(teamStat.getFgMade());
            teamStatDO.setThreeAttempt(teamStat.getThreeAttempt());
            teamStatDO.setThreeMade(teamStat.getThreeMade());
            teamStatDO.setAssist(teamStat.getAssist());
            teamStatDO.setRebound(teamStat.getRebound());
            teamStatDO.setOffRebound(teamStat.getOffRebound());
            teamStatDO.setDefRebound(teamStat.getDefRebound());
            teamStatDO.setSteal(teamStat.getSteal());
            teamStatDO.setBlock(teamStat.getBlock());
            teamStatDO.setDunk(teamStat.getDunk());
            teamStatDO.setPaintScore(teamStat.getPaintScore());
            teamStatDO.setSecondChanceScore(teamStat.getSecondChanceScore());
            teamStatDO.setTurnoverToScore(teamStat.getTurnoverToScore());
            teamStatDO.setMaxLead(teamStat.getMaxLead());
            teamStatDO.setModifier(modifier);

            LambdaUpdateWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(MatchTeamStatsDO::getId, teamStat.getId())
                    .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

            count += matchTeamStatsMapper.update(teamStatDO, wrapper);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByMatchId(Long matchId, Long modifier) {
        LambdaUpdateWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MatchTeamStatsDO::getMatchId, matchId)
                .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchTeamStatsDO teamStatDO = new MatchTeamStatsDO();
        teamStatDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        teamStatDO.setModifier(modifier);

        return matchTeamStatsMapper.update(teamStatDO, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByMatchIdAndTeamType(Long matchId, Integer teamType, Long modifier) {
        LambdaUpdateWrapper<MatchTeamStatsDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MatchTeamStatsDO::getMatchId, matchId)
                .eq(MatchTeamStatsDO::getTeamType, teamType)
                .eq(MatchTeamStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchTeamStatsDO teamStatDO = new MatchTeamStatsDO();
        teamStatDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        teamStatDO.setModifier(modifier);

        return matchTeamStatsMapper.update(teamStatDO, wrapper);
    }
}