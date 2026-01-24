package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;

/**
 * TODO 标签创建查询对象
 *
 * <p>封装创建标签所需的参数。
 *
 * @author csxuhuan
 */
public class TodoTagCreateQuery {

    /** 标签名称 */
    private String name;

    /**
     * 构造标签创建查询对象
     *
     * @param name 标签名称
     */
    public TodoTagCreateQuery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 转换为标签领域实体
     *
     * <p>工厂方法，将查询对象转换为领域实体。
     *
     * @return 新建的标签领域实体（未持久化）
     */
    public TodoTag toTodoTag() {
        return new TodoTag(null, name, null, null);
    }
}
