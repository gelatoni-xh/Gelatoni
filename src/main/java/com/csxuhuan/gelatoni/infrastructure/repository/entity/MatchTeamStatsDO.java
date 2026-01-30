package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;

import java.time.LocalDateTime;

/**
 * 比赛队伍统计数据表数据库对象
 *
 * <p>存储每场比赛中两支队伍的整体统计数据。
 *
 * @author Gelatoni
 */
@TableName(value = "match_team_stats")
public class MatchTeamStatsDO {

    /** 队伍统计ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联比赛ID */
    @TableField("match_id")
    private Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    @TableField("team_type")
    private Integer teamType;

    /** 队伍总得分 */
    @TableField("score")
    private Integer score;

    /** 投篮尝试数 */
    @TableField("fg_attempt")
    private Integer fgAttempt;

    /** 投篮命中数 */
    @TableField("fg_made")
    private Integer fgMade;

    /** 三分尝试数 */
    @TableField("three_attempt")
    private Integer threeAttempt;

    /** 三分命中数 */
    @TableField("three_made")
    private Integer threeMade;

    /** 助攻数 */
    @TableField("assist")
    private Integer assist;

    /** 总篮板数 */
    @TableField("rebound")
    private Integer rebound;

    /** 进攻篮板 */
    @TableField("off_rebound")
    private Integer offRebound;

    /** 防守篮板 */
    @TableField("def_rebound")
    private Integer defRebound;

    /** 抢断数 */
    @TableField("steal")
    private Integer steal;

    /** 盖帽数 */
    @TableField("block")
    private Integer block;

    /** 灌篮次数 */
    @TableField("dunk")
    private Integer dunk;

    /** 内线得分 */
    @TableField("paint_score")
    private Integer paintScore;

    /** 二次进攻得分 */
    @TableField("second_chance_score")
    private Integer secondChanceScore;

    /** 利用失误得分 */
    @TableField("turnover_to_score")
    private Integer turnoverToScore;

    /** 最大领先分差 */
    @TableField("max_lead")
    private Integer maxLead;

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
    public MatchTeamStatsDO() {
    }

    public MatchTeamStatsDO(Long id, Long matchId, Integer teamType, Integer score, Integer fgAttempt,
                           Integer fgMade, Integer threeAttempt, Integer threeMade, Integer assist,
                           Integer rebound, Integer offRebound, Integer defRebound, Integer steal,
                           Integer block, Integer dunk, Integer paintScore, Integer secondChanceScore,
                           Integer turnoverToScore, Integer maxLead, Long creator, Long modifier,
                           LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.matchId = matchId;
        this.teamType = teamType;
        this.score = score;
        this.fgAttempt = fgAttempt;
        this.fgMade = fgMade;
        this.threeAttempt = threeAttempt;
        this.threeMade = threeMade;
        this.assist = assist;
        this.rebound = rebound;
        this.offRebound = offRebound;
        this.defRebound = defRebound;
        this.steal = steal;
        this.block = block;
        this.dunk = dunk;
        this.paintScore = paintScore;
        this.secondChanceScore = secondChanceScore;
        this.turnoverToScore = turnoverToScore;
        this.maxLead = maxLead;
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