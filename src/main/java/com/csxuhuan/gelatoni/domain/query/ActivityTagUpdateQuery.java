package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;

/**
 * 活动标签更新查询对象
 *
 * <p>封装更新标签所需的参数。支持部分更新，
 * 只更新非 null 的字段。
 *
 * @author csxuhuan
 */
public class ActivityTagUpdateQuery {

    /** 标签 ID，必填 */
    private Long id;

    /** 标签名称，null 表示不更新 */
    private String name;

    /** 标签颜色，null 表示不更新 */
    private String color;

    /**
     * 构造标签更新查询对象
     *
     * @param id 标签 ID，必填
     * @param name 标签名称
     * @param color 标签颜色
     */
    public ActivityTagUpdateQuery(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

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

    /**
     * 转换为标签领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体，用于更新操作。
     * 注意：只包含非 null 的字段。
     *
     * @return 标签领域实体
     */
    public ActivityTag toActivityTag() {
        return new ActivityTag(id, name, color, null, null);
    }
}
