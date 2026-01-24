package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.domain.model.entity.Notice;
import com.csxuhuan.gelatoni.domain.query.NoticeCreateQuery;
import com.csxuhuan.gelatoni.domain.query.NoticePageQuery;
import com.csxuhuan.gelatoni.domain.result.PageResult;

/**
 * 公告应用服务接口
 *
 * <p>应用层服务，作为 interfaces 层与 domain 层之间的桥梁。
 * 负责编排领域服务，处理跨领域的业务逻辑，以及事务管理。
 *
 * <p>职责说明：
 * <ul>
 *     <li>协调多个领域服务完成业务用例</li>
 *     <li>处理事务边界（如需要）</li>
 *     <li>调用基础设施服务（如消息队列、缓存等）</li>
 * </ul>
 *
 * <p>注意：应用服务不应包含业务规则，业务规则应该放在领域层。
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.application.service.impl.NoticeAppServiceImpl
 */
public interface NoticeAppService {

    /**
     * 分页查询公告
     *
     * <p>查询所有未删除的公告，按创建时间倒序排列。
     *
     * @param query 分页查询条件，包含 pageNo 和 pageSize
     * @return 分页结果，包含公告列表和分页信息
     */
    PageResult<Notice> pageQuery(NoticePageQuery query);

    /**
     * 新增公告
     *
     * <p>创建一条新的公告记录，创建时间和修改时间由数据库自动填充。
     *
     * @param query 创建公告的参数，包含 title 和 content
     * @return 影响的行数（通常为 1）
     */
    int create(NoticeCreateQuery query);
}

