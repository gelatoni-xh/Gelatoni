package com.csxuhuan.gelatoni.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * 对手统计数据返回 DTO
 *
 * <p>统计对阵各个对手的胜负情况和胜率
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpponentStatsDTO {

    /** 赛季标识 */
    private String season;

    /** 对手统计列表 */
    private List<OpponentRecord> opponents;

    public OpponentStatsDTO() {
    }

    public OpponentStatsDTO(String season, List<OpponentRecord> opponents) {
        this.season = season;
        this.opponents = opponents;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<OpponentRecord> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<OpponentRecord> opponents) {
        this.opponents = opponents;
    }

    /**
     * 单个对手的统计记录
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpponentRecord {
        /** 对手球员名称 */
        private String playerName;

        /** 总对阵次数 */
        private Integer totalGames;

        /** 胜场数 */
        private Integer wins;

        /** 负场数 */
        private Integer losses;

        /** 胜率（0-1） */
        private Double winRate;

        /** 场均净胜分 */
        private Double avgPointDifferential;

        public OpponentRecord() {
        }

        public OpponentRecord(String playerName, Integer totalGames, Integer wins, Integer losses, Double winRate, Double avgPointDifferential) {
            this.playerName = playerName;
            this.totalGames = totalGames;
            this.wins = wins;
            this.losses = losses;
            this.winRate = winRate;
            this.avgPointDifferential = avgPointDifferential;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public Integer getTotalGames() {
            return totalGames;
        }

        public void setTotalGames(Integer totalGames) {
            this.totalGames = totalGames;
        }

        public Integer getWins() {
            return wins;
        }

        public void setWins(Integer wins) {
            this.wins = wins;
        }

        public Integer getLosses() {
            return losses;
        }

        public void setLosses(Integer losses) {
            this.losses = losses;
        }

        public Double getWinRate() {
            return winRate;
        }

        public void setWinRate(Double winRate) {
            this.winRate = winRate;
        }

        public Double getAvgPointDifferential() {
            return avgPointDifferential;
        }

        public void setAvgPointDifferential(Double avgPointDifferential) {
            this.avgPointDifferential = avgPointDifferential;
        }
    }

}
