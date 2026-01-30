package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.dto.MatchGameDTO;
import com.csxuhuan.gelatoni.application.dto.MatchGameDetailDTO;
import com.csxuhuan.gelatoni.application.service.MatchGameAppService;
import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.query.MatchGameCreateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGameUpdateQuery;
import com.csxuhuan.gelatoni.domain.query.MatchGamePageQuery;
import com.csxuhuan.gelatoni.domain.service.MatchGameDomainService;
import com.csxuhuan.gelatoni.application.assembler.MatchGameAssembler;

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
    private final MatchGameAssembler assembler = new MatchGameAssembler();

    public MatchGameAppServiceImpl(MatchGameDomainService matchGameDomainService) {
        this.matchGameDomainService = matchGameDomainService;
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
}