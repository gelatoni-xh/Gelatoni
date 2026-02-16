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

    /** 统计摘要 */
    private Summary summary;

    public OpponentStatsDTO() {
    }

    public OpponentStatsDTO(String season, List<OpponentRecord> opponents, Summary summary) {
        this.season = season;
        this.opponents = opponents;
        this.summary = summary;
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

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
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

        /** 净胜分 */
        private Integer pointDifferential;

        public OpponentRecord() {
        }

        public OpponentRecord(String playerName, Integer totalGames, Integer wins, Integer losses, Double winRate, Integer pointDifferential) {
            this.playerName = playerName;
            this.totalGames = totalGames;
            this.wins = wins;
            this.losses = losses;
            this.winRate = winRate;
            this.pointDifferential = pointDifferential;
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

        public Integer getPointDifferential() {
            return pointDifferential;
        }

        public void setPointDifferential(Integer pointDifferential) {
            this.pointDifferential = pointDifferential;
        }
    }

    /**
     * 统计摘要
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        /** 对手总数 */
        private Integer totalOpponents;

        /** 最难对手的负场数 */
        private Integer maxLosses;

        /** 平均胜率 */
        private Double avgWinRate;

        public Summary() {
        }

        public Summary(Integer totalOpponents, Integer maxLosses, Double avgWinRate) {
            this.totalOpponents = totalOpponents;
            this.maxLosses = maxLosses;
            this.avgWinRate = avgWinRate;
        }

        public Integer getTotalOpponents() {
            return totalOpponents;
        }

        public void setTotalOpponents(Integer totalOpponents) {
            this.totalOpponents = totalOpponents;
        }

        public Integer getMaxLosses() {
            return maxLosses;
        }

        public void setMaxLosses(Integer maxLosses) {
            this.maxLosses = maxLosses;
        }

        public Double getAvgWinRate() {
            return avgWinRate;
        }

        public void setAvgWinRate(Double avgWinRate) {
            this.avgWinRate = avgWinRate;
        }
    }
}
