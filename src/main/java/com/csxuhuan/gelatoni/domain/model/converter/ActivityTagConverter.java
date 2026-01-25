package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityTag;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ActivityTagDO;

/**
 * ActivityTag 领域对象转换器
 *
 * <p>负责活动标签领域层与基础设施层之间的对象转换。
 *
 * @author csxuhuan
 */
public class ActivityTagConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private ActivityTagConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * @param tagDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static ActivityTag toDomain(ActivityTagDO tagDO) {
        if (tagDO == null) {
            return null;
        }

        return new ActivityTag(
                tagDO.getId(),
                tagDO.getName(),
                tagDO.getColor(),
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
    public static ActivityTagDO toDO(ActivityTag tag) {
        if (tag == null) {
            return null;
        }
        ActivityTagDO tagDO = new ActivityTagDO();
        tagDO.setId(tag.getId());
        tagDO.setName(tag.getName());
        tagDO.setColor(tag.getColor());
        tagDO.setCreateTime(tag.getCreateTime());
        tagDO.setModifiedTime(tag.getModifiedTime());
        tagDO.setIsDeleted(DeletedEnum.NOT_DELETED.getValue());
        return tagDO;
    }
}
