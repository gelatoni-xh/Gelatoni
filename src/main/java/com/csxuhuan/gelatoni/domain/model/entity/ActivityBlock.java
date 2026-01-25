package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 活动记录领域实体
 *
 * <p>代表一个"有效的活动记录"，是领域模型的核心对象。
 * 领域实体只关注业务属性，不关心数据库实现细节。
 *
 * <p>业务属性：
 * <ul>
 *     <li>tagId - 关联的标签ID（必填）</li>
 *     <li>activityDate - 活动日期</li>
 *     <li>startTime - 开始时间（必须是 00 或 30 分）</li>
 *     <li>endTime - 结束时间（必须是 00 或 30 分，且 > startTime）</li>
 *     <li>detail - 具体活动描述（可选）</li>
 * </ul>
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author csxuhuan
 */
public class ActivityBlock {

    /** 活动记录唯一标识 */
    private final Long id;

    /** 关联的标签 ID */
    private final Long tagId;

    /** 活动日期 */
    private final LocalDate activityDate;

    /** 开始时间 */
    private final LocalTime startTime;

    /** 结束时间 */
    private final LocalTime endTime;

    /** 具体活动描述 */
    private final String detail;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造活动记录领域对象
     *
     * @param id           活动记录 ID，新建时可为 null
     * @param tagId        关联的标签 ID
     * @param activityDate 活动日期
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param detail       具体活动描述，可为 null
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public ActivityBlock(Long id, Long tagId, LocalDate activityDate, LocalTime startTime, LocalTime endTime,
                        String detail, LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.tagId = tagId;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.detail = detail;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public Long getTagId() {
        return tagId;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getDetail() {
        return detail;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
