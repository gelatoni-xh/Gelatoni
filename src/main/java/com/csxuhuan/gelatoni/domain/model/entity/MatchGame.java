package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 比赛领域实体
 *
 * <p>代表一场完整的篮球比赛，包含比赛基本信息。
 * 领域实体只关注业务属性，不关心数据库实现细节。
 *
 * <p>业务属性：
 * <ul>
 *     <li>season - 赛季标识</li>
 *     <li>seasonMatchNo - 赛季第几场比赛</li>
 *     <li>matchTime - 比赛时间</li>
 *     <li>isRobot - 是否为机器人对局</li>
 *     <li>myScore - 我方得分</li>
 *     <li>oppScore - 对方得分</li>
 *     <li>result - 比赛结果</li>
 *     <li>remark - 备注</li>
 * </ul>
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author Gelatoni
 */
public class MatchGame {

    /** 比赛唯一标识 */
    private final Long id;

    /** 赛季标识，例如 S1、2026Q1 */
    private final String season;

    /** 该赛季第几场比赛 */
    private final Integer seasonMatchNo;

    /** 比赛开始时间 */
    private final LocalDateTime matchTime;

    /** 是否为机器人对局：1=机器人，0=真人 */
    private final Boolean isRobot;

    /** 我方最终得分 */
    private final Integer myScore;

    /** 对方最终得分 */
    private final Integer oppScore;

    /** 比赛结果：1=我方胜利，0=我方失败 */
    private final Boolean result;

    /** 比赛备注 */
    private final String remark;

    /** 创建人 */
    private final Long creator;

    /** 修改人 */
    private final Long modifier;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造比赛领域对象
     *
     * @param id             比赛 ID，新建时可为 null
     * @param season         赛季标识
     * @param seasonMatchNo  赛季第几场比赛
     * @param matchTime      比赛开始时间
     * @param isRobot        是否为机器人对局
     * @param myScore        我方最终得分
     * @param oppScore       对方最终得分
     * @param result         比赛结果
     * @param remark         比赛备注
     * @param creator        创建人，新建时需指定
     * @param modifier       修改人，新建时与创建人相同
     * @param createTime     创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime   修改时间，新建时可为 null（由数据库填充）
     */
    public MatchGame(Long id, String season, Integer seasonMatchNo, LocalDateTime matchTime,
                     Boolean isRobot, Integer myScore, Integer oppScore, Boolean result,
                     String remark, Long creator, Long modifier, LocalDateTime createTime,
                     LocalDateTime modifiedTime) {
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
    }

    public Long getId() {
        return id;
    }

    public String getSeason() {
        return season;
    }

    public Integer getSeasonMatchNo() {
        return seasonMatchNo;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public Boolean getIsRobot() {
        return isRobot;
    }

    public Integer getMyScore() {
        return myScore;
    }

    public Integer getOppScore() {
        return oppScore;
    }

    public Boolean getResult() {
        return result;
    }

    public String getRemark() {
        return remark;
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