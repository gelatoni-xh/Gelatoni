package com.csxuhuan.gelatoni.interfaces.web.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 创建或更新活动记录请求
 */
public class ActivityBlockCreateOrUpdateRequest {

    /**
     * 标签ID
     */
    @NotNull(message = "标签ID不能为空")
    private Long tagId;

    /**
     * 活动日期
     */
    @NotNull(message = "活动日期不能为空")
    private LocalDate activityDate;

    /**
     * 开始时间（必须是 00 或 30 分）
     */
    @NotNull(message = "开始时间不能为空")
    private LocalTime startTime;

    /**
     * 结束时间（必须是 00 或 30 分，且 > startTime）
     */
    @NotNull(message = "结束时间不能为空")
    private LocalTime endTime;

    /**
     * 具体活动描述
     */
    @Size(max = 512, message = "活动描述长度不能超过512个字符")
    private String detail;

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
}
