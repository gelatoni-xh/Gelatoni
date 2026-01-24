package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.TodoTag;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.TodoTagDO;

/**
 * TodoTag 领域对象转换器
 *
 * 职责：
 * - 将基础设施层的 DO 转换为领域对象
 * - 防止基础设施细节渗透到 Domain
 */
public class TodoTagConverter {

    private TodoTagConverter() {
        // 工具类，不允许实例化
    }

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
