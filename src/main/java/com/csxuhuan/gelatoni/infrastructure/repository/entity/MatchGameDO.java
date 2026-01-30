package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;

import java.time.LocalDateTime;

/**
 * 比赛主表数据库对象
 *
 * <p>存储比赛的基本信息，包括比分、时间、赛季等。
 *
 * @author Gelatoni
 */
@TableName(value = "match_game")
public class MatchGameDO {

    /** 比赛ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 赛季标识，例如 S1、2026Q1 */
    @TableField("season")
    private String season;

    /** 该赛季第几场比赛 */
    @TableField("season_match_no")
    private Integer seasonMatchNo;

    /** 比赛开始时间 */
    @TableField("match_time")
    private LocalDateTime matchTime;

    /** 是否为机器人对局：1=机器人，0=真人 */
    @TableField("is_robot")
    private Boolean isRobot;

    /** 我方最终得分 */
    @TableField("my_score")
    private Integer myScore;

    /** 对方最终得分 */
    @TableField("opp_score")
    private Integer oppScore;

    /** 比赛结果：1=我方胜利，0=我方失败 */
    @TableField("result")
    private Boolean result;

    /** 比赛备注 */
    @TableField("remark")
    private String remark;

    /** 创建人 */
    @TableField("creator")
    private Long creator;

    /** 修改人 */
    @TableField("modifier")
    private Long modifier;

    /** 记录创建时间 */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 记录更新时间 */
    @TableField("modified_time")
    private LocalDateTime modifiedTime;

    /** 是否删除：0-否，1-是 */
    @TableField("is_deleted")
    private Boolean isDeleted;

    // 构造函数
    public MatchGameDO() {
    }

    public MatchGameDO(Long id, String season, Integer seasonMatchNo, LocalDateTime matchTime, 
                      Boolean isRobot, Integer myScore, Integer oppScore, Boolean result, 
                      String remark, Long creator, Long modifier, LocalDateTime createTime, 
                      LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.season = season;
        this.seasonMatchNo = seasonMatchNo;
        this.matchTime = matchTime;
        this.isRobot = isRobot;
        this.myScore = myScore;
        this.oppScore = oppScore;
        this.result = result;
        this.remark = remark;
        this.creator = creator;
        this.modifier = modifier;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.isDeleted = isDeleted;
    }

    // Getter 和 Setter
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

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}