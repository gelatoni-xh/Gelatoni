package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoTagDO;

/**
 * TodoTag 领域对象转换器
 *
 * <p>负责 TODO 标签领域层与基础设施层之间的对象转换。
 *
 * @author csxuhuan
 * @see NoticeConverter 类似的转换器实现
 */
public class TodoTagConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private TodoTagConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * @param tagDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static TodoTag toDomain(TodoTagDO tagDO) {
        if (tagDO == null) {
            return null;
        }

        return new TodoTag(
                tagDO.getId(),
                tagDO.getName(),
                tagDO.getCreateTime(),
                tagDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * @param tag 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static TodoTagDO toDO(TodoTag tag) {
        if (tag == null) {
            return null;
        }
        return new TodoTagDO(
                tag.getId(),
                tag.getName(),
                tag.getCreateTime(),
                tag.getModifiedTime(),
                DeletedEnum.NOT_DELETED.getValue()
        );
    }
}
