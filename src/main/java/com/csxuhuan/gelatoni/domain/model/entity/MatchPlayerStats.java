package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 比赛球员统计数据领域实体
 *
 * <p>代表比赛中一名球员的详细统计数据。
 * 领域实体只关注业务属性，不关心数据库实现细节。
 *
 * <p>业务属性：
 * <ul>
 *     <li>matchId - 关联比赛ID</li>
 *     <li>teamType - 队伍类型（我方/对方）</li>
 *     <li>userName/playerName - 玩家和球员信息</li>
 *     <li>rating/isMvp/isSvp - 评级信息</li>
 *     <li>score/assist/rebound - 基础统计数据</li>
 *     <li>fgAttempt/fgMade/threeAttempt/threeMade - 投篮数据</li>
 *     <li>turnover/maxScoringRun - 特殊统计数据</li>
 * </ul>
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author Gelatoni
 */
public class MatchPlayerStats {

    /** 球员比赛数据唯一标识 */
    private final Long id;

    /** 关联比赛ID */
    private final Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    private final Integer teamType;

    /** 使用人昵称（我方记录，对方可为空） */
    private final String userName;

    /** 使用球员名称 */
    private final String playerName;

    /** 比赛评分 */
    private final Integer rating;

    /** 是否MVP：1=是 */
    private final Boolean isMvp;

    /** 是否SVP：1=是 */
    private final Boolean isSvp;

    /** 得分 */
    private final Integer score;

    /** 助攻 */
    private final Integer assist;

    /** 篮板 */
    private final Integer rebound;

    /** 抢断 */
    private final Integer steal;

    /** 盖帽 */
    private final Integer block;

    /** 失误 */
    private final Integer turnover;

    /** 灌篮次数 */
    private final Integer dunk;

    /** 投篮尝试数 */
    private final Integer fgAttempt;

    /** 投篮命中数 */
    private final Integer fgMade;

    /** 三分尝试数 */
    private final Integer threeAttempt;

    /** 三分命中数 */
    private final Integer threeMade;

    /** 中投次数（官方仅提供次数） */
    private final Integer midCount;

    /** 个人最大连续得分 */
    private final Integer maxScoringRun;

    /** 创建人 */
    private final Long creator;

    /** 修改人 */
    private final Long modifier;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造球员统计数据领域对象
     *
     * @param id              球员比赛数据 ID，新建时可为 null
     * @param matchId         关联比赛ID
     * @param teamType        队伍类型：1=我方，2=对方
     * @param userName        使用人昵称（我方记录，对方可为空）
     * @param playerName      使用球员名称
     * @param rating          比赛评分
     * @param isMvp           是否MVP：1=是
     * @param isSvp           是否SVP：1=是
     * @param score           得分
     * @param assist          助攻
     * @param rebound         篮板
     * @param steal           抢断
     * @param block           盖帽
     * @param turnover        失误
     * @param dunk            灌篮次数
     * @param fgAttempt       投篮尝试数
     * @param fgMade          投篮命中数
     * @param threeAttempt    三分尝试数
     * @param threeMade       三分命中数
     * @param midCount        中投次数
     * @param maxScoringRun   个人最大连续得分
     * @param creator         创建人，新建时需指定
     * @param modifier        修改人，新建时与创建人相同
     * @param createTime      创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime    修改时间，新建时可为 null（由数据库填充）
     */
    public MatchPlayerStats(Long id, Long matchId, Integer teamType, String userName, String playerName,
                            Integer rating, Boolean isMvp, Boolean isSvp, Integer score, Integer assist,
                            Integer rebound, Integer steal, Integer block, Integer turnover, Integer dunk,
                            Integer fgAttempt, Integer fgMade, Integer threeAttempt, Integer threeMade,
                            Integer midCount, Integer maxScoringRun, Long creator, Long modifier,
                            LocalDateTime createTime, LocalDateTime modifiedTime) {
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
    }

    public Long getId() {
        return id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Integer getTeamType() {
        return teamType;
    }

    public String getUserName() {
        return userName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getRating() {
        return rating;
    }

    public Boolean getIsMvp() {
        return isMvp;
    }

    public Boolean getIsSvp() {
        return isSvp;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getAssist() {
        return assist;
    }

    public Integer getRebound() {
        return rebound;
    }

    public Integer getSteal() {
        return steal;
    }

    public Integer getBlock() {
        return block;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public Integer getDunk() {
        return dunk;
    }

    public Integer getFgAttempt() {
        return fgAttempt;
    }

    public Integer getFgMade() {
        return fgMade;
    }

    public Integer getThreeAttempt() {
        return threeAttempt;
    }

    public Integer getThreeMade() {
        return threeMade;
    }

    public Integer getMidCount() {
        return midCount;
    }

    public Integer getMaxScoringRun() {
        return maxScoringRun;
    }

    public Long getCreator() {
        return creator;
    }

    public Long getModifier() {
        return modifier;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}