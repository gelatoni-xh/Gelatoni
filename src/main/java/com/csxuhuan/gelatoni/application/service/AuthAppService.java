package com.csxuhuan.gelatoni.application.service;

import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import com.csxuhuan.gelatoni.application.service.impl.AuthAppServiceImpl;

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
 * @see AuthAppServiceImpl
 */
public interface AuthAppService {

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

    /**
     * 用户登录
     *
     * <p>接收用户名和密码，完成登录验证并返回用户信息和 Token：
     * <ol>
     *     <li>校验用户是否存在</li>
     *     <li>校验密码是否正确</li>
     *     <li>获取用户角色和权限信息</li>
     *     <li>生成 JWT Token</li>
     *     <li>返回用户信息（包含用户 DTO、角色码列表、权限码列表、Token）</li>
     * </ol>
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息结果，包含用户 DTO、角色码列表、权限码列表、Token
     * @throws RuntimeException 如果用户不存在或密码错误
     */
    UserInfoDTO login(String username, String password);

    /**
     * 根据角色编码获取用户信息
     *
     * <p>返回指定角色对应的用户信息，包括：
     * <ul>
     *     <li>用户基本信息（匿名用户时为 null）</li>
     *     <li>角色编码列表（包含指定的角色）</li>
     *     <li>权限编码列表（该角色拥有的所有权限）</li>
     * </ul>
     *
     * <p>实现说明：
     * <ol>
     *     <li>根据角色编码查询角色信息</li>
     *     <li>根据角色ID查询权限ID列表</li>
     *     <li>根据权限ID列表查询权限信息，提取权限码</li>
     *     <li>使用 Assembler 转换为 DTO 并返回</li>
     * </ol>
     *
     * @param roleCode 角色编码
     * @return 用户信息结果，包含用户 DTO（匿名用户时为 null）、角色码列表、权限码列表
     */
    UserInfoDTO getUserInfoByRoleCode(String roleCode);
}
