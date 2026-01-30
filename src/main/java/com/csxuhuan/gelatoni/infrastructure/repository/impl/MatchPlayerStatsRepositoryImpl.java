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
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchGameDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.MatchGameMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 比赛球员统计仓储实现类
 *
 * @author Gelatoni
 */
@Repository
public class MatchPlayerStatsRepositoryImpl implements MatchPlayerStatsRepository {

    private final MatchPlayerStatsMapper matchPlayerStatsMapper;
    private final MatchGameMapper matchGameMapper;

    public MatchPlayerStatsRepositoryImpl(MatchPlayerStatsMapper matchPlayerStatsMapper, MatchGameMapper matchGameMapper) {
        this.matchPlayerStatsMapper = matchPlayerStatsMapper;
        this.matchGameMapper = matchGameMapper;
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
    public List<MatchPlayerStats> findMyPlayerStatsForStats(String season, Boolean excludeRobot) {
        // 使用 QueryWrapper 构建复杂查询
        LambdaQueryWrapper<MatchPlayerStatsDO> playerStatsWrapper = Wrappers.lambdaQuery();
        playerStatsWrapper.eq(MatchPlayerStatsDO::getTeamType, 1) // 只查询我方球员 (team_type = 1)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        // 构建比赛查询条件
        LambdaQueryWrapper<MatchGameDO> gameWrapper = Wrappers.lambdaQuery();
        gameWrapper.eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        
        // 如果提供了赛季，则按赛季过滤
        if (season != null && !season.isEmpty()) {
            gameWrapper.eq(MatchGameDO::getSeason, season);
        }
        
        // 如果excludeRobot = true，则排除机器人比赛(is_robot = 1)
        if (Boolean.TRUE.equals(excludeRobot)) {
            gameWrapper.eq(MatchGameDO::getIsRobot, false);
        }
        
        // 查询符合条件的比赛ID
        List<Long> gameIds = matchGameMapper.selectList(gameWrapper).stream()
                .map(MatchGameDO::getId)
                .collect(Collectors.toList());
        
        // 如果没有符合条件的比赛，则返回空列表
        if (gameIds.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 在球员统计中过滤这些比赛ID
        playerStatsWrapper.in(MatchPlayerStatsDO::getMatchId, gameIds);
        
        // 执行查询
        List<MatchPlayerStatsDO> matchPlayerStatsDOList = matchPlayerStatsMapper.selectList(playerStatsWrapper);
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
    public int deleteByMatchId(Long matchId, Long modifier) {
        LambdaUpdateWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MatchPlayerStatsDO::getMatchId, matchId)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchPlayerStatsDO playerStatDO = new MatchPlayerStatsDO();
        playerStatDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        playerStatDO.setModifier(modifier);

        return matchPlayerStatsMapper.update(playerStatDO, wrapper);
    }

    @Override
    public List<String> findDistinctPlayerNames(Integer teamType) {
        LambdaQueryWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchPlayerStatsDO::getTeamType, teamType)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .isNotNull(MatchPlayerStatsDO::getPlayerName)
                .ne(MatchPlayerStatsDO::getPlayerName, "")
                .select(MatchPlayerStatsDO::getPlayerName)
                .groupBy(MatchPlayerStatsDO::getPlayerName)
                .orderByAsc(MatchPlayerStatsDO::getPlayerName);

        return matchPlayerStatsMapper.selectList(wrapper).stream()
                .map(MatchPlayerStatsDO::getPlayerName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findDistinctMyUserNames() {
        LambdaQueryWrapper<MatchPlayerStatsDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchPlayerStatsDO::getTeamType, 1)
                .eq(MatchPlayerStatsDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .isNotNull(MatchPlayerStatsDO::getUserName)
                .ne(MatchPlayerStatsDO::getUserName, "")
                .select(MatchPlayerStatsDO::getUserName)
                .groupBy(MatchPlayerStatsDO::getUserName)
                .orderByAsc(MatchPlayerStatsDO::getUserName);

        return matchPlayerStatsMapper.selectList(wrapper).stream()
                .map(MatchPlayerStatsDO::getUserName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}