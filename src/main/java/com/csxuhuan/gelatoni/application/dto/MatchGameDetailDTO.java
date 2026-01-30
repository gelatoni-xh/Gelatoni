package com.csxuhuan.gelatoni.application.dto;

import java.util.List;

/**
 * 比赛详情数据传输对象（DTO）
 *
 * <p>用于向前端返回单场比赛的完整统计数据。
 *
 * @author Gelatoni
 */
public class MatchGameDetailDTO {

    /** 比赛基础信息 */
    private MatchGameDTO matchGame;

    /** 我方队伍整体统计数据 */
    private List<MatchTeamStatsDTO> myTeamStats;

    /** 对方队伍整体统计数据 */
    private List<MatchTeamStatsDTO> opponentTeamStats;

    /** 我方全部球员数据 */
    private List<MatchPlayerStatsDTO> myPlayerStats;

    /** 对方全部球员数据 */
    private List<MatchPlayerStatsDTO> opponentPlayerStats;

    public MatchGameDetailDTO(MatchGameDTO matchGame, List<MatchTeamStatsDTO> myTeamStats,
                              List<MatchTeamStatsDTO> opponentTeamStats, List<MatchPlayerStatsDTO> myPlayerStats,
                              List<MatchPlayerStatsDTO> opponentPlayerStats) {
        this.matchGame = matchGame;
        this.myTeamStats = myTeamStats;
        this.opponentTeamStats = opponentTeamStats;
        this.myPlayerStats = myPlayerStats;
        this.opponentPlayerStats = opponentPlayerStats;
    }

    public MatchGameDTO getMatchGame() {
        return matchGame;
    }

    public void setMatchGame(MatchGameDTO matchGame) {
        this.matchGame = matchGame;
    }

    public List<MatchTeamStatsDTO> getMyTeamStats() {
        return myTeamStats;
    }

    public void setMyTeamStats(List<MatchTeamStatsDTO> myTeamStats) {
        this.myTeamStats = myTeamStats;
    }

    public List<MatchTeamStatsDTO> getOpponentTeamStats() {
        return opponentTeamStats;
    }

    public void setOpponentTeamStats(List<MatchTeamStatsDTO> opponentTeamStats) {
        this.opponentTeamStats = opponentTeamStats;
    }

    public List<MatchPlayerStatsDTO> getMyPlayerStats() {
        return myPlayerStats;
    }

    public void setMyPlayerStats(List<MatchPlayerStatsDTO> myPlayerStats) {
        this.myPlayerStats = myPlayerStats;
    }

    public List<MatchPlayerStatsDTO> getOpponentPlayerStats() {
        return opponentPlayerStats;
    }

    public void setOpponentPlayerStats(List<MatchPlayerStatsDTO> opponentPlayerStats) {
        this.opponentPlayerStats = opponentPlayerStats;
    }
}