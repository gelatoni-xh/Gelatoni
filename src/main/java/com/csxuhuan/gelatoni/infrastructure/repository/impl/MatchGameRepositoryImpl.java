package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.model.converter.MatchGameConverter;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchGameRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchTeamStatsRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchPlayerStatsRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.MatchGameDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.MatchGameMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛仓储实现类
 *
 * @author Gelatoni
 */
@Repository
public class MatchGameRepositoryImpl implements MatchGameRepository {

    private final MatchGameMapper matchGameMapper;

    public MatchGameRepositoryImpl(MatchGameMapper matchGameMapper) {
        this.matchGameMapper = matchGameMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchGame> findAll() {
        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByDesc(MatchGameDO::getMatchTime);
        List<MatchGameDO> matchGameDOList = matchGameMapper.selectList(wrapper);
        return matchGameDOList.stream()
                .map(MatchGameConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchGame findById(Long id) {
        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getId, id)
                .eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchGameDO matchGameDO = matchGameMapper.selectOne(wrapper);
        return MatchGameConverter.toDomain(matchGameDO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchGame> findBySeason(String season) {
        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getSeason, season)
                .eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue())
                .orderByDesc(MatchGameDO::getMatchTime);
        List<MatchGameDO> matchGameDOList = matchGameMapper.selectList(wrapper);
        return matchGameDOList.stream()
                .map(MatchGameConverter::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int create(MatchGame game, Long creator) {
        MatchGameDO matchGameDO = MatchGameConverter.toDO(game);
        matchGameDO.setCreator(creator);
        matchGameDO.setModifier(creator);
        return matchGameMapper.insert(matchGameDO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(MatchGame game, Long modifier) {
        MatchGameDO matchGameDO = new MatchGameDO();
        matchGameDO.setId(game.getId());
        matchGameDO.setSeason(game.getSeason());
        matchGameDO.setSeasonMatchNo(game.getSeasonMatchNo());
        matchGameDO.setMatchTime(game.getMatchTime());
        matchGameDO.setIsRobot(game.getIsRobot());
        matchGameDO.setMyScore(game.getMyScore());
        matchGameDO.setOppScore(game.getOppScore());
        matchGameDO.setResult(game.getResult());
        matchGameDO.setRemark(game.getRemark());
        matchGameDO.setModifier(modifier);

        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getId, game.getId())
                .eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        return matchGameMapper.update(matchGameDO, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Long id, Long modifier) {
        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getId, id)
                .eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());

        MatchGameDO matchGameDO = new MatchGameDO();
        matchGameDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        matchGameDO.setModifier(modifier);

        return matchGameMapper.update(matchGameDO, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        LambdaQueryWrapper<MatchGameDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MatchGameDO::getIsDeleted, DeletedEnum.NOT_DELETED.getValue());
        return matchGameMapper.selectCount(wrapper).intValue();
    }
}