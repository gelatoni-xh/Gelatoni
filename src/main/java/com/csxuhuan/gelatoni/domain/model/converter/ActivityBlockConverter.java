package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.entity.ActivityBlock;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.ActivityBlockDO;

/**
 * ActivityBlock 领域对象转换器
 *
 * <p>负责活动记录领域层与基础设施层之间的对象转换。
 *
 * @author csxuhuan
 */
public class ActivityBlockConverter {

    /**
     * 私有构造函数，工具类不允许实例化
     */
    private ActivityBlockConverter() {
    }

    /**
     * 将数据库对象转换为领域实体
     *
     * @param blockDO 数据库对象
     * @return 领域实体，如果输入为 null 则返回 null
     */
    public static ActivityBlock toDomain(ActivityBlockDO blockDO) {
        if (blockDO == null) {
            return null;
        }

        return new ActivityBlock(
                blockDO.getId(),
                blockDO.getTagId(),
                blockDO.getActivityDate(),
                blockDO.getStartTime(),
                blockDO.getEndTime(),
                blockDO.getDetail(),
                blockDO.getCreateTime(),
                blockDO.getModifiedTime()
        );
    }

    /**
     * 将领域实体转换为数据库对象
     *
     * @param block 领域实体
     * @return 数据库对象，如果输入为 null 则返回 null
     */
    public static ActivityBlockDO toDO(ActivityBlock block) {
        if (block == null) {
            return null;
        }
        ActivityBlockDO blockDO = new ActivityBlockDO();
        blockDO.setId(block.getId());
        blockDO.setTagId(block.getTagId());
        blockDO.setActivityDate(block.getActivityDate());
        blockDO.setStartTime(block.getStartTime());
        blockDO.setEndTime(block.getEndTime());
        blockDO.setDetail(block.getDetail());
        blockDO.setCreateTime(block.getCreateTime());
        blockDO.setModifiedTime(block.getModifiedTime());
        blockDO.setIsDeleted(DeletedEnum.NOT_DELETED.getValue());
        return blockDO;
    }
}
