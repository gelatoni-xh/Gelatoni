package com.csxuhuan.gelatoni.domain.model.converter;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.NoticeDO;

/**
 * Notice 领域对象转换器
 *
 * 职责：
 * - 将基础设施层的 DO 转换为领域对象
 * - 防止基础设施细节渗透到 Domain
 */
public class NoticeConverter {

    private NoticeConverter() {
        // 工具类，不允许实例化
    }

    public static Notice toDomain(NoticeDO noticeDO) {
        if (noticeDO == null) {
            return null;
        }

        return new Notice(
                noticeDO.getId(),
                noticeDO.getTitle(),
                noticeDO.getContent(),
                noticeDO.getCreateTime(),
                noticeDO.getModifiedTime()
        );
    }
}
