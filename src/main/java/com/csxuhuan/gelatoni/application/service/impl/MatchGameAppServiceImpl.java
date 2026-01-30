package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameStatsDTO;
import com.csxuhuan.gelatoni.application.service.MatchGameAppService;
import com.csxuhuan.gelatoni.application.service.MatchGameStatsCalculator;
import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.domain.service.MatchGameDomainService;
import com.csxuhuan.gelatoni.application.assembler.MatchGameAssembler;
import com.csxuhuan.gelatoni.infrastructure.repository.MatchPlayerStatsRepository;
import com.csxuhuan.gelatoni.interfaces.web.request.MatchGameStatsRequest;

import java.util.List;
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
    private final MatchPlayerStatsRepository matchPlayerStatsRepository;
    private final MatchGameAssembler assembler = new MatchGameAssembler();

    public MatchGameAppServiceImpl(MatchGameDomainService matchGameDomainService,
                                  MatchPlayerStatsRepository matchPlayerStatsRepository) {
        this.matchGameDomainService = matchGameDomainService;
        this.matchPlayerStatsRepository = matchPlayerStatsRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long createMatchGame(MatchGameCreateQuery query) {
        return matchGameDomainService.createMatchGame(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMatchGame(MatchGameUpdateQuery query) {
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
    public MatchGameDTO getMatchGameById(Long id) {
        MatchGame matchGame = matchGameDomainService.getMatchGameById(id);
        return assembler.toDTO(matchGame);
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
    public int getMatchGameCount() {
        return matchGameDomainService.getMatchGameCount();
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
        // 约束：本期不做预计算/缓存/中间表，因此直接一次查询 + 内存聚合。

        String season = request == null ? null : request.getSeason();
        MatchGameStatsRequest.StatsDimension reqDim = request == null ? null : request.getDimension();

        MatchGameStatsDTO.Dimension dim = reqDim == MatchGameStatsRequest.StatsDimension.USER
                ? MatchGameStatsDTO.Dimension.USER
                : MatchGameStatsDTO.Dimension.PLAYER;

        List<MatchPlayerStats> myPlayerStats = matchPlayerStatsRepository.findMyPlayerStatsForStats(season);
        return MatchGameStatsCalculator.calculate(season, dim, myPlayerStats);
    }
}