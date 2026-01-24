package com.csxuhuan.gelatoni.domain.service;

import com.csxuhuan.gelatoni.domain.model.entity.User;

/**
 * 用户领域服务接口
 *
 * <p>定义用户相关的领域行为。领域服务封装了不适合放在实体中的业务逻辑，
 * 通常涉及多个实体的协作或外部资源的访问。
 *
 * <p>设计说明：
 * <ul>
 *     <li>领域服务不关心技术实现（如 MyBatis），通过 Repository 接口访问数据</li>
 *     <li>业务规则的验证应该在领域服务中实现</li>
 * </ul>
 *
 * @author csxuhuan
 * @see com.csxuhuan.gelatoni.domain.service.impl.UserDomainServiceImpl
 */
public interface UserDomainService {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户领域对象，如果不存在则返回 null
     */
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户领域对象，如果不存在则返回 null
     */
    User findById(Long id);
}
