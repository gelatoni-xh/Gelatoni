package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 比赛数据传输对象（DTO）
 *
 * <p>用于向前端返回比赛信息。
 *
 * @author Gelatoni
 */
public class MatchGameDTO {

    /** 比赛ID */
    private Long id;

    /** 赛季标识，例如 S1、2026Q1 */
    private String season;

    /** 比赛开始时间 */
    private LocalDateTime matchTime;

    /** 是否为机器人对局：1=机器人，0=真人 */
    private Boolean isRobot;

    /** 我方最终得分 */
    private Integer myScore;

    /** 对方最终得分 */
    private Integer oppScore;

    /** 比赛结果：1=我方胜利，0=我方失败 */
    private Boolean result;

    /** 比赛备注 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    public Boolean getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(Boolean isRobot) {
        this.isRobot = isRobot;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public void setMyScore(Integer myScore) {
        this.myScore = myScore;
    }

    public Integer getOppScore() {
        return oppScore;
    }

    public void setOppScore(Integer oppScore) {
        this.oppScore = oppScore;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}