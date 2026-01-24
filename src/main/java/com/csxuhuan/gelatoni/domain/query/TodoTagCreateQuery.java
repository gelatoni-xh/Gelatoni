package com.csxuhuan.gelatoni.domain.query;

import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;

/**
 * 标签创建查询条件（领域对象）
 */
public class TodoTagCreateQuery {

    /**
     * 标签名称
     */
    private String name;

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
     * 转换为领域层实体
     *
     * @return 领域层实体
     */
    public TodoTag toTodoTag() {
        return new TodoTag(null, name, null, null);
    }
}
