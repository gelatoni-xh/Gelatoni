package com.csxuhuan.gelatoni.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;

/**
 * 公告仓储接口
 *
 * 约定：
 * - Repository 内部使用 DO + MyBatis-Plus
 * - 对外只返回领域对象 Notice
 */
public interface NoticeRepository {

    /**
     * 分页查询公告
     *
     * @param pageNo   页码，从 1 开始
     * @param pageSize 每页大小
     * @return 公告分页结果（领域对象）
     */
    IPage<Notice> pageNotices(long pageNo, long pageSize);

    /**
     * 新增公告
     *
     * @param notice 新增的领域对象
     * @return 新增的ID
     */
    int createNotice(Notice notice);

    /**
     * 删除公告（软删除）
     *
     * @param id 公告ID
     * @param modifier 修改人ID
     * @return 受影响行数
     */
    int deleteNotice(Long id, Long modifier);
}