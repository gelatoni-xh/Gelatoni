package com.csxuhuan.gelatoni.infrastructure.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;

import java.time.LocalDateTime;

/**
 * 比赛球员统计数据表数据库对象
 *
 * <p>存储每场比赛中球员的详细统计数据。
 *
 * @author Gelatoni
 */
@TableName(value = "match_player_stats")
public class MatchPlayerStatsDO {

    /** 球员比赛数据ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联比赛ID */
    @TableField("match_id")
    private Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    @TableField("team_type")
    private Integer teamType;

    /** 使用人昵称（我方记录，对方可为空） */
    @TableField("user_name")
    private String userName;

    /** 使用球员名称 */
    @TableField("player_name")
    private String playerName;

    /** 比赛评分 */
    @TableField("rating")
    private Double rating;

    /** 是否MVP：1=是 */
    @TableField("is_mvp")
    private Boolean isMvp;

    /** 是否SVP：1=是 */
    @TableField("is_svp")
    private Boolean isSvp;

    /** 得分 */
    @TableField("score")
    private Integer score;

    /** 助攻 */
    @TableField("assist")
    private Integer assist;

    /** 篮板 */
    @TableField("rebound")
    private Integer rebound;

    /** 抢断 */
    @TableField("steal")
    private Integer steal;

    /** 盖帽 */
    @TableField("block")
    private Integer block;

    /** 失误 */
    @TableField("turnover")
    private Integer turnover;

    /** 灌篮次数 */
    @TableField("dunk")
    private Integer dunk;

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

    /** 中投次数（官方仅提供次数） */
    @TableField("mid_count")
    private Integer midCount;

    /** 个人最大连续得分 */
    @TableField("max_scoring_run")
    private Integer maxScoringRun;

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
    public MatchPlayerStatsDO() {
    }

    public MatchPlayerStatsDO(Long id, Long matchId, Integer teamType, String userName, String playerName,
                            Double rating, Boolean isMvp, Boolean isSvp, Integer score, Integer assist,
                            Integer rebound, Integer steal, Integer block, Integer turnover, Integer dunk,
                            Integer fgAttempt, Integer fgMade, Integer threeAttempt, Integer threeMade,
                            Integer midCount, Integer maxScoringRun, Long creator, Long modifier,
                            LocalDateTime createTime, LocalDateTime modifiedTime, Boolean isDeleted) {
        this.id = id;
        this.matchId = matchId;
        this.teamType = teamType;
        this.userName = userName;
        this.playerName = playerName;
        this.rating = rating;
        this.isMvp = isMvp;
        this.isSvp = isSvp;
        this.score = score;
        this.assist = assist;
        this.rebound = rebound;
        this.steal = steal;
        this.block = block;
        this.turnover = turnover;
        this.dunk = dunk;
        this.fgAttempt = fgAttempt;
        this.fgMade = fgMade;
        this.threeAttempt = threeAttempt;
        this.threeMade = threeMade;
        this.midCount = midCount;
        this.maxScoringRun = maxScoringRun;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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