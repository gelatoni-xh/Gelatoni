package com.csxuhuan.gelatoni.application.dto;

import java.time.LocalDateTime;

/**
 * 活动标签数据传输对象（DTO）
 *
 * <p>用于向前端返回标签信息。
 *
 * <p>字段说明：
 * <ul>
 *     <li>id - 标签唯一标识</li>
 *     <li>name - 标签名称</li>
 *     <li>color - 标签颜色</li>
 *     <li>createTime - 创建时间</li>
 * </ul>
 *
 * @author csxuhuan
 */
public class ActivityTagDTO {

    /** 标签唯一标识 */
    private Long id;

    /** 标签名称 */
    private String name;

    /** 标签颜色 */
    private String color;

    /** 创建时间 */
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
