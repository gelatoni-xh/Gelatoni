package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 活动记录创建或更新查询对象
 *
 * <p>封装创建或更新活动记录所需的参数。
 * 幂等规则：以 (user_id, date, startTime) 作为幂等键。
 *
 * @author csxuhuan
 */
public class ActivityBlockCreateOrUpdateQuery {

    /** 标签 ID */
    private Long tagId;

    /** 活动日期 */
    private LocalDate activityDate;

    /** 开始时间 */
    private LocalTime startTime;

    /** 结束时间 */
    private LocalTime endTime;

    /** 具体活动描述 */
    private String detail;

    /**
     * 构造活动记录创建或更新查询对象
     *
     * @param tagId 标签 ID
     * @param activityDate 活动日期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param detail 具体活动描述
     */
    public ActivityBlockCreateOrUpdateQuery(Long tagId, LocalDate activityDate, LocalTime startTime, LocalTime endTime, String detail) {
        this.tagId = tagId;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.detail = detail;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * 转换为活动记录领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体。
     *
     * @return 新建的活动记录领域实体（未持久化）
     */
    public ActivityBlock toActivityBlock() {
        return new ActivityBlock(null, tagId, activityDate, startTime, endTime, detail, null, null);
    }
}
