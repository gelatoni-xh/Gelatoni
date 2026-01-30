package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 比赛队伍统计数据传输对象（DTO）
 *
 * <p>用于向前端返回比赛队伍统计信息。
 *
 * @author Gelatoni
 */
public class MatchTeamStatsDTO {

    /** 队伍统计ID */
    private Long id;

    /** 关联比赛ID */
    private Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    private Integer teamType;

    /** 队伍总得分 */
    private Integer score;

    /** 投篮尝试数 */
    private Integer fgAttempt;

    /** 投篮命中数 */
    private Integer fgMade;

    /** 三分尝试数 */
    private Integer threeAttempt;

    /** 三分命中数 */
    private Integer threeMade;

    /** 助攻数 */
    private Integer assist;

    /** 总篮板数 */
    private Integer rebound;

    /** 进攻篮板 */
    private Integer offRebound;

    /** 防守篮板 */
    private Integer defRebound;

    /** 抢断数 */
    private Integer steal;

    /** 盖帽数 */
    private Integer block;

    /** 灌篮次数 */
    private Integer dunk;

    /** 内线得分 */
    private Integer paintScore;

    /** 二次进攻得分 */
    private Integer secondChanceScore;

    /** 利用失误得分 */
    private Integer turnoverToScore;

    /** 最大领先分差 */
    private Integer maxLead;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Integer getOffRebound() {
        return offRebound;
    }

    public void setOffRebound(Integer offRebound) {
        this.offRebound = offRebound;
    }

    public Integer getDefRebound() {
        return defRebound;
    }

    public void setDefRebound(Integer defRebound) {
        this.defRebound = defRebound;
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

    public Integer getDunk() {
        return dunk;
    }

    public void setDunk(Integer dunk) {
        this.dunk = dunk;
    }

    public Integer getPaintScore() {
        return paintScore;
    }

    public void setPaintScore(Integer paintScore) {
        this.paintScore = paintScore;
    }

    public Integer getSecondChanceScore() {
        return secondChanceScore;
    }

    public void setSecondChanceScore(Integer secondChanceScore) {
        this.secondChanceScore = secondChanceScore;
    }

    public Integer getTurnoverToScore() {
        return turnoverToScore;
    }

    public void setTurnoverToScore(Integer turnoverToScore) {
        this.turnoverToScore = turnoverToScore;
    }

    public Integer getMaxLead() {
        return maxLead;
    }

    public void setMaxLead(Integer maxLead) {
        this.maxLead = maxLead;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}