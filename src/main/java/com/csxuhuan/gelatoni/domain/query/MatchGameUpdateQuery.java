package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.MatchGame;
import com.csxuhuan.gelatoni.domain.model.entity.MatchTeamStats;
import com.csxuhuan.gelatoni.domain.model.entity.MatchPlayerStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 比赛更新查询对象
 *
 * <p>用于接收更新比赛的参数，支持两种更新方式：
 * <ul>
 *     <li>只更新比赛基础信息</li>
 *     <li>一次性更新整场比赛全部数据（比赛 + 队伍统计 + 所有球员数据）</li>
 * </ul>
 *
 * @author Gelatoni
 */
public class MatchGameUpdateQuery {

    // 比赛ID（必须）
    private Long id;

    // 比赛基础信息
    private String season;
    private Integer seasonMatchNo;
    private LocalDateTime matchTime;
    private Boolean isRobot;
    private Integer myScore;
    private Integer oppScore;
    private Boolean result;
    private String remark;
    private Long modifier;

    // 队伍统计数据（可选）
    private List<MatchTeamStats> teamStatsList;

    // 球员统计数据（可选，如果提供则必须包含全部球员数据）
    private List<MatchPlayerStats> playerStatsList;

    public MatchGameUpdateQuery(Long id, String season, Integer seasonMatchNo, LocalDateTime matchTime, 
                               Boolean isRobot, Integer myScore, Integer oppScore, Boolean result, 
                               String remark, Long modifier, List<MatchTeamStats> teamStatsList, 
                               List<MatchPlayerStats> playerStatsList) {
        this.id = id;
        this.season = season;
        this.seasonMatchNo = seasonMatchNo;
        this.matchTime = matchTime;
        this.isRobot = isRobot;
        this.myScore = myScore;
        this.oppScore = oppScore;
        this.result = result;
        this.remark = remark;
        this.modifier = modifier;
        this.teamStatsList = teamStatsList;
        this.playerStatsList = playerStatsList;
    }

    /**
     * 转换为比赛领域实体
     */
    public MatchGame toMatchGame() {
        return new MatchGame(id, season, seasonMatchNo, matchTime, isRobot, myScore, oppScore, 
                result, remark, null, modifier, null, null);
    }

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

    public Integer getSeasonMatchNo() {
        return seasonMatchNo;
    }

    public void setSeasonMatchNo(Integer seasonMatchNo) {
        this.seasonMatchNo = seasonMatchNo;
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

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public List<MatchTeamStats> getTeamStatsList() {
        return teamStatsList;
    }

    public void setTeamStatsList(List<MatchTeamStats> teamStatsList) {
        this.teamStatsList = teamStatsList;
    }

    public List<MatchPlayerStats> getPlayerStatsList() {
        return playerStatsList;
    }

    public void setPlayerStatsList(List<MatchPlayerStats> playerStatsList) {
        this.playerStatsList = playerStatsList;
    }
}