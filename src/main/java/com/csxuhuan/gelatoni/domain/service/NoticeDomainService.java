package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;

/**
 * 公告领域服务接口
 *
 * <p>定义公告相关的领域行为。领域服务封装了不适合放在实体中的业务逻辑，
 * 通常涉及多个实体的协作或外部资源的访问。
 *
 * <p>设计说明：
 * <ul>
 *     <li>分页查询属于"集合获取规则"，是领域层的职责</li>
 *     <li>领域服务不关心技术实现（如 MyBatis），通过 Repository 接口访问数据</li>
 *     <li>业务规则的验证应该在领域服务中实现</li>
 * </ul>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.domain.service.impl.NoticeDomainServiceImpl
 */
public interface NoticeDomainService {

    /**
     * 分页查询公告
     *
     * <p>查询所有未删除的公告，按创建时间倒序排列。
     *
     * @param query 分页查询条件
     * @return 分页结果
     */
    PageResult<Notice> pageQuery(NoticePageQuery query);

    /**
     * 创建公告
     *
     * <p>创建一条新的公告记录。
     *
     * @param query 创建条件
     * @return 影响的行数
     */
    int create(NoticeCreateQuery query);

    /**
     * 删除公告
     *
     * @param id 公告ID
     * @param modifier 修改人ID
     * @return 影响的行数
     */
    int delete(Long id, Long modifier);
}