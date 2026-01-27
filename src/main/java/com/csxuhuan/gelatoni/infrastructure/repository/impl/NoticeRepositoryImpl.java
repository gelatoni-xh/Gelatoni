package com.csxuhuan.gelatoni.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csxuhuan.gelatoni.domain.model.common.DeletedEnum;
import com.csxuhuan.gelatoni.domain.model.converter.NoticeConverter;
import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.infrastructure.repository.NoticeRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.entity.NoticeDO;
import com.csxuhuan.gelatoni.infrastructure.repository.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告仓储实现
 *
 * <p>实现 {@link NoticeRepository} 接口，使用 MyBatis-Plus 进行数据访问。
 *
 * <p>职责边界（DDD 仓储模式）：
 * <ul>
 *     <li>NoticeDO 只在此类中出现，不向上层暴露</li>
 *     <li>负责 DO ↔ Domain 的转换（通过 Converter）</li>
 *     <li>屏蔽 MyBatis-Plus 的实现细节，上层只看到领域对象</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Repository
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeMapper noticeMapper;

    /**
     * 构造函数，注入 Mapper
     *
     * @param noticeMapper 公告 Mapper
     */
    public NoticeRepositoryImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：
     * <ol>
     *     <li>使用 MyBatis-Plus 的 Page 对象执行分页查询</li>
     *     <li>将查询结果的 DO 列表转换为领域对象列表</li>
     *     <li>按创建时间倒序排序</li>
     *     <li>封装为领域层的分页对象返回</li>
     * </ol>
     */
    @Override
    public IPage<Notice> pageNotices(long pageNo, long pageSize) {

        // MyBatis-Plus 约束：Page 泛型必须是 DO 类型
        Page<NoticeDO> page = new Page<>(pageNo, pageSize);

        // 执行分页查询
        IPage<NoticeDO> doPage = noticeMapper.selectPage(page, null);

        // DO → Domain 转换，并按时间倒序排列
        List<Notice> notices = doPage.getRecords()
                .stream()
                .map(NoticeConverter::toDomain)
                .sorted((a, b) -> b.getCreateTime().compareTo(a.getCreateTime()))
                .collect(Collectors.toList());

        // 构造领域分页对象
        Page<Notice> domainPage = new Page<>(
                doPage.getCurrent(),
                doPage.getSize(),
                doPage.getTotal()
        );
        domainPage.setRecords(notices);

        return domainPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createNotice(Notice notice) {
        return noticeMapper.insert(NoticeConverter.toDO(notice));
    }

    @Override
    public int deleteNotice(Long id, Long modifier) {
        LambdaUpdateWrapper<NoticeDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(NoticeDO::getId, id);

        NoticeDO noticeDO = new NoticeDO();
        noticeDO.setIsDeleted(DeletedEnum.DELETED.getValue());
        noticeDO.setModifiedTime(LocalDateTime.now());

        return noticeMapper.update(noticeDO, wrapper);
    }
}