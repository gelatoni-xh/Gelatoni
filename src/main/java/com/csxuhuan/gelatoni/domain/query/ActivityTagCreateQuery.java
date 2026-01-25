package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;

/**
 * 活动标签创建查询对象
 *
 * <p>封装创建标签所需的参数。
 *
 * @author csxuhuan
 */
public class ActivityTagCreateQuery {

    /** 标签名称 */
    private String name;

    /** 标签颜色 */
    private String color;

    /**
     * 构造标签创建查询对象
     *
     * @param name 标签名称
     * @param color 标签颜色
     */
    public ActivityTagCreateQuery(String name, String color) {
        this.name = name;
        this.color = color;
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
     * <p>工厂方法，将查询对象转换为领域实体。
     *
     * @return 新建的标签领域实体（未持久化）
     */
    public ActivityTag toActivityTag() {
        return new ActivityTag(null, name, color, null, null);
    }
}
