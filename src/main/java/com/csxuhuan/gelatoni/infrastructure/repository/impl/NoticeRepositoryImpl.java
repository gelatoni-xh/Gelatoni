package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csxuhuan.gelatoni.domain.model.converter.NoticeConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.infrastructure.repository.NoticeRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.NoticeDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告仓储实现
 *
 * 职责边界：
 * - 只在这里出现 NoticeDO
 * - 负责 DO → Domain 的转换
 * - 屏蔽 MyBatis-Plus 对上层的影响
 */
@Repository
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeMapper noticeMapper;

    public NoticeRepositoryImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public IPage<Notice> pageNotices(long pageNo, long pageSize) {

        // 1️⃣ MyBatis-Plus 强约束：Page 泛型必须是 DO
        Page<NoticeDO> page = new Page<>(pageNo, pageSize);

        // 2️⃣ 执行分页查询
        IPage<NoticeDO> doPage = noticeMapper.selectPage(page, null);

        // 3️⃣ DO → Domain
        List<Notice> notices = doPage.getRecords()
                .stream()
                .map(NoticeConverter::toDomain)
                .collect(Collectors.toList());

        // 4️⃣ 构造领域分页对象
        Page<Notice> domainPage = new Page<>(
                doPage.getCurrent(),
                doPage.getSize(),
                doPage.getTotal()
        );
        domainPage.setRecords(notices);

        return domainPage;
    }
}