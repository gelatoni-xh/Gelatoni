package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;

/**
 * 用户应用服务接口
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
 * @see com.csxuhuan.gelatoni.application.service.impl.UserAppServiceImpl
 */
public interface UserAppService {

    /**
     * 根据用户名获取用户及其所有角色码、权限码
     *
     * <p>返回用户完整信息，包括：
     * <ul>
     *     <li>用户基本信息</li>
     *     <li>用户拥有的所有角色编码列表</li>
     *     <li>用户拥有的所有权限编码列表</li>
     * </ul>
     *
     * @param username 用户名
     * @return 用户信息结果，包含用户 DTO、角色码列表、权限码列表
     */
    UserInfoDTO getUserWithRolesAndPermissions(String username);
}
