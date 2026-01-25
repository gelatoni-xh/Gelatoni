package com.csxuhuan.gelatoni.domain.model.entity;

import java.time.LocalDateTime;

/**
 * 活动标签领域实体
 *
 * <p>代表一个"有效的活动标签"，用于对活动记录进行分类管理。
 * 一个标签可以关联多个活动记录。
 *
 * <p>设计说明：
 * 采用不可变对象模式，所有字段为 final，保证线程安全和数据一致性。
 *
 * @author csxuhuan
 */
public class ActivityTag {

    /** 标签唯一标识 */
    private final Long id;

    /** 标签名称 */
    private final String name;

    /** 标签颜色，如 #FFAA00 */
    private final String color;

    /** 创建时间 */
    private final LocalDateTime createTime;

    /** 最后修改时间 */
    private final LocalDateTime modifiedTime;

    /**
     * 构造活动标签领域对象
     *
     * @param id           标签 ID，新建时可为 null
     * @param name         标签名称
     * @param color        标签颜色
     * @param createTime   创建时间，新建时可为 null（由数据库填充）
     * @param modifiedTime 修改时间，新建时可为 null（由数据库填充）
     */
    public ActivityTag(Long id, String name, String color, LocalDateTime createTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }
}
