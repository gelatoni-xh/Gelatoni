package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 比赛球员统计数据传输对象（DTO）
 *
 * <p>用于向前端返回比赛球员统计信息。
 *
 * @author Gelatoni
 */
public class MatchPlayerStatsDTO {

    /** 球员比赛数据ID */
    private Long id;

    /** 关联比赛ID */
    private Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    private Integer teamType;

    /** 使用人昵称（我方记录，对方可为空） */
    private String userName;

    /** 使用球员名称 */
    private String playerName;

    /** 比赛评分 */
    private Integer rating;

    /** 是否MVP：1=是 */
    private Boolean isMvp;

    /** 是否SVP：1=是 */
    private Boolean isSvp;

    /** 得分 */
    private Integer score;

    /** 助攻 */
    private Integer assist;

    /** 篮板 */
    private Integer rebound;

    /** 抢断 */
    private Integer steal;

    /** 盖帽 */
    private Integer block;

    /** 失误 */
    private Integer turnover;

    /** 灌篮次数 */
    private Integer dunk;

    /** 投篮尝试数 */
    private Integer fgAttempt;

    /** 投篮命中数 */
    private Integer fgMade;

    /** 三分尝试数 */
    private Integer threeAttempt;

    /** 三分命中数 */
    private Integer threeMade;

    /** 中投次数（官方仅提供次数） */
    private Integer midCount;

    /** 个人最大连续得分 */
    private Integer maxScoringRun;

    /** 创建时间 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Integer getTeamType() {
        return teamType;
    }

    public void setTeamType(Integer teamType) {
        this.teamType = teamType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIsMvp() {
        return isMvp;
    }

    public void setIsMvp(Boolean isMvp) {
        this.isMvp = isMvp;
    }

    public Boolean getIsSvp() {
        return isSvp;
    }

    public void setIsSvp(Boolean isSvp) {
        this.isSvp = isSvp;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist = assist;
    }

    public Integer getRebound() {
        return rebound;
    }

    public void setRebound(Integer rebound) {
        this.rebound = rebound;
    }

    public Integer getSteal() {
        return steal;
    }

    public void setSteal(Integer steal) {
        this.steal = steal;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    public Integer getDunk() {
        return dunk;
    }

    public void setDunk(Integer dunk) {
        this.dunk = dunk;
    }

    public Integer getFgAttempt() {
        return fgAttempt;
    }

    public void setFgAttempt(Integer fgAttempt) {
        this.fgAttempt = fgAttempt;
    }

    public Integer getFgMade() {
        return fgMade;
    }

    public void setFgMade(Integer fgMade) {
        this.fgMade = fgMade;
    }

    public Integer getThreeAttempt() {
        return threeAttempt;
    }

    public void setThreeAttempt(Integer threeAttempt) {
        this.threeAttempt = threeAttempt;
    }

    public Integer getThreeMade() {
        return threeMade;
    }

    public void setThreeMade(Integer threeMade) {
        this.threeMade = threeMade;
    }

    public Integer getMidCount() {
        return midCount;
    }

    public void setMidCount(Integer midCount) {
        this.midCount = midCount;
    }

    public Integer getMaxScoringRun() {
        return maxScoringRun;
    }

    public void setMaxScoringRun(Integer maxScoringRun) {
        this.maxScoringRun = maxScoringRun;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}