package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 比赛队伍统计数据领域实体
 *
 * <p>代表比赛中一支队伍的整体统计数据。
 * 领域实体只关注业务属性，不关心数据库实现细节。
 *
 * <p>业务属性：
 * <ul>
 *     <li>matchId - 关联比赛ID</li>
 *     <li>teamType - 队伍类型（我方/对方）</li>
 *     <li>score - 总得分</li>
 *     <li>fgAttempt/fgMade - 投篮数据</li>
 *     <li>threeAttempt/threeMade - 三分数据</li>
 *     <li>assist/rebound/steal/block - 基础统计数据</li>
 *     <li>dunk/paintScore/secondChanceScore - 特殊统计数据</li>
 * </ul>
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author Gelatoni
 */
public class MatchTeamStats {

    /** 队伍统计唯一标识 */
    private final Long id;

    /** 关联比赛ID */
    private final Long matchId;

    /** 队伍类型：1=我方，2=对方 */
    private final Integer teamType;

    /** 队伍总得分 */
    private final Integer score;

    /** 投篮尝试数 */
    private final Integer fgAttempt;

    /** 投篮命中数 */
    private final Integer fgMade;

    /** 三分尝试数 */
    private final Integer threeAttempt;

    /** 三分命中数 */
    private final Integer threeMade;

    /** 助攻数 */
    private final Integer assist;

    /** 总篮板数 */
    private final Integer rebound;

    /** 进攻篮板 */
    private final Integer offRebound;

    /** 防守篮板 */
    private final Integer defRebound;

    /** 抢断数 */
    private final Integer steal;

    /** 盖帽数 */
    private final Integer block;

    /** 灌篮次数 */
    private final Integer dunk;

    /** 内线得分 */
    private final Integer paintScore;

    /** 二次进攻得分 */
    private final Integer secondChanceScore;

    /** 利用失误得分 */
    private final Integer turnoverToScore;

    /** 最大领先分差 */
    private final Integer maxLead;

    /** 创建人 */
    private final Long creator;

    /** 修改人 */
    private final Long modifier;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造队伍统计数据领域对象
     *
     * @param id                队伍统计 ID，新建时可为 null
     * @param matchId           关联比赛ID
     * @param teamType          队伍类型：1=我方，2=对方
     * @param score             队伍总得分
     * @param fgAttempt         投篮尝试数
     * @param fgMade            投篮命中数
     * @param threeAttempt      三分尝试数
     * @param threeMade         三分命中数
     * @param assist            助攻数
     * @param rebound           总篮板数
     * @param offRebound        进攻篮板
     * @param defRebound        防守篮板
     * @param steal             抢断数
     * @param block             盖帽数
     * @param dunk              灌篮次数
     * @param paintScore        内线得分
     * @param secondChanceScore 二次进攻得分
     * @param turnoverToScore   利用失误得分
     * @param maxLead           最大领先分差
     * @param creator           创建人，新建时需指定
     * @param modifier          修改人，新建时与创建人相同
     * @param createTime        创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime      修改时间，新建时可为 null（由数据库填充）
     */
    public MatchTeamStats(Long id, Long matchId, Integer teamType, Integer score, Integer fgAttempt,
                          Integer fgMade, Integer threeAttempt, Integer threeMade, Integer assist,
                          Integer rebound, Integer offRebound, Integer defRebound, Integer steal,
                          Integer block, Integer dunk, Integer paintScore, Integer secondChanceScore,
                          Integer turnoverToScore, Integer maxLead, Long creator, Long modifier,
                          LocalDateTime createTime, LocalDateTime modifiedTime) {
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

    public Integer getScore() {
        return score;
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

    public Integer getAssist() {
        return assist;
    }

    public Integer getRebound() {
        return rebound;
    }

    public Integer getOffRebound() {
        return offRebound;
    }

    public Integer getDefRebound() {
        return defRebound;
    }

    public Integer getSteal() {
        return steal;
    }

    public Integer getBlock() {
        return block;
    }

    public Integer getDunk() {
        return dunk;
    }

    public Integer getPaintScore() {
        return paintScore;
    }

    public Integer getSecondChanceScore() {
        return secondChanceScore;
    }

    public Integer getTurnoverToScore() {
        return turnoverToScore;
    }

    public Integer getMaxLead() {
        return maxLead;
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