package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoItemDO;

/**
 * TodoItem 领域对象转换器
 *
 * 职责：
 * - 将基础设施层的 DO 转换为领域对象
 * - 防止基础设施细节渗透到 Domain
 */
public class TodoItemConverter {

    private TodoItemConverter() {
        // 工具类，不允许实例化
    }

    public static TodoItem toDomain(TodoItemDO itemDO) {
        if (itemDO == null) {
            return null;
        }

        return new TodoItem(
                itemDO.getId(),
                itemDO.getContent(),
                itemDO.getCompleted(),
                itemDO.getTagId(),
                itemDO.getCreateTime(),
                itemDO.getModifiedTime()
        );
    }

    public static TodoItemDO toDO(TodoItem item) {
        if (item == null) {
            return null;
        }
        return new TodoItemDO(
                item.getId(),
                item.getContent(),
                item.getCompleted(),
                item.getTagId(),
                item.getCreateTime(),
                item.getModifiedTime(),
                DeletedEnum.NOT_DELETED.getValue()
        );
    }
}
