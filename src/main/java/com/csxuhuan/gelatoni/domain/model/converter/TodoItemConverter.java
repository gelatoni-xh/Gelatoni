package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.TodoItem;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoItemDO;

/**
 * TodoItem 领域对象转换器
 *
 * <p>负责 TODO 项领域层与基础设施层之间的对象转换。
 *
 * @author csxuhuan
 * @see NoticeConverter 类似的转换器实现
 */
public class TodoItemConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private TodoItemConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * @param itemDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
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

    /**
     * 将领域实体转换为数据库对象
     *
     * @param item 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
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
