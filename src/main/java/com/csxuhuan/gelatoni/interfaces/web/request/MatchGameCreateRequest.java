package com.csxuhuan.gelatoni.interfaces.web.request;

import com.csxuhuan.gelatoni.application.dto.MatchTeamStatsDTO;
import com.csxuhuan.gelatoni.application.dto.MatchPlayerStatsDTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 比赛创建请求 DTO
 *
 * <p>用于接收前端创建比赛的请求参数。
 * 支持两种创建方式：
 * <ul>
 *     <li>只保存比赛基础信息</li>
 *     <li>一次性保存整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
 * </ul>
 *
 * @author Gelatoni
 */
public class MatchGameCreateRequest {

    /** 赛季标识，例如 S1、2026Q1 */
    @NotNull(message = "赛季不能为空")
    private String season;

    /** 比赛开始时间 */
    @NotNull(message = "比赛时间不能为空")
    private LocalDateTime matchTime;

    /** 是否为机器人对局：1=机器人，0=真人 */
    @NotNull(message = "是否机器人对局不能为空")
    private Boolean isRobot;

    /** 我方最终得分 */
    @NotNull(message = "我方得分不能为空")
    private Integer myScore;

    /** 对方最终得分 */
    @NotNull(message = "对方得分不能为空")
    private Integer oppScore;

    /** 比赛结果：1=我方胜利，0=我方失败 */
    @NotNull(message = "比赛结果不能为空")
    private Boolean result;

    /** 比赛备注 */
    private String remark;

    /** 创建人 */
    @NotNull(message = "创建人不能为空")
    private Long creator;

    /** 队伍统计数据（可选） */
    private List<MatchTeamStatsDTO> teamStatsList;

    /** 球员统计数据（可选，如果提供则必须包含全部球员数据） */
    private List<MatchPlayerStatsDTO> playerStatsList;

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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public List<MatchTeamStatsDTO> getTeamStatsList() {
        return teamStatsList;
    }

    public void setTeamStatsList(List<MatchTeamStatsDTO> teamStatsList) {
        this.teamStatsList = teamStatsList;
    }

    public List<MatchPlayerStatsDTO> getPlayerStatsList() {
        return playerStatsList;
    }

    public void setPlayerStatsList(List<MatchPlayerStatsDTO> playerStatsList) {
        this.playerStatsList = playerStatsList;
    }
}