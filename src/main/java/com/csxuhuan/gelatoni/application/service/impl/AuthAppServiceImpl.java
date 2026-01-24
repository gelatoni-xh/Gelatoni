package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.assembler.UserAssembler;
import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import com.csxuhuan.gelatoni.application.service.AuthAppService;
import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.domain.service.UserDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.PermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.RolePermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.RoleRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户应用服务实现类
 *
 * <p>实现 {@link AuthAppService} 接口，委托给领域服务处理具体业务逻辑。
 * 负责编排多个领域服务和仓储，完成跨领域的业务用例。
 *
 * <p>扩展点：
 * <ul>
 *     <li>添加事务管理（@Transactional）</li>
 *     <li>添加缓存处理</li>
 *     <li>添加消息发送</li>
 *     <li>组合多个领域服务的调用</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Service
public class AuthAppServiceImpl implements AuthAppService {

    private final UserDomainService userDomainService;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserAssembler userAssembler;

    /**
     * 构造函数，注入依赖
     *
     * @param userDomainService         用户领域服务
     * @param userRoleRepository        用户角色关联仓储
     * @param roleRepository            角色仓储
     * @param rolePermissionRepository  角色权限关联仓储
     * @param permissionRepository      权限仓储
     * @param userAssembler             用户装配器
     */
    public AuthAppServiceImpl(UserDomainService userDomainService,
                              UserRoleRepository userRoleRepository,
                              RoleRepository roleRepository,
                              RolePermissionRepository rolePermissionRepository,
                              PermissionRepository permissionRepository,
                              UserAssembler userAssembler) {
        this.userDomainService = userDomainService;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.userAssembler = userAssembler;
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：
     * <ol>
     *     <li>根据用户名查询用户信息</li>
     *     <li>查询用户关联的角色ID列表</li>
     *     <li>根据角色ID列表查询角色信息，提取角色码</li>
     *     <li>根据角色ID列表查询权限ID列表</li>
     *     <li>根据权限ID列表查询权限信息，提取权限码</li>
     *     <li>使用 Assembler 转换为 DTO 并返回</li>
     * </ol>
     */
    @Override
    public UserInfoDTO getUserWithRolesAndPermissions(String username) {
        // 1. 根据用户名查询用户
        User user = userDomainService.findByUsername(username);
        if (user == null) {
            return userAssembler.toUserInfoDTO(null, Collections.emptyList(), Collections.emptyList());
        }

        // 2. 查询用户关联的角色ID列表
        List<Long> roleIds = userRoleRepository.findRoleIdsByUserId(user.getId());
        if (roleIds.isEmpty()) {
            return userAssembler.toUserInfoDTO(user, Collections.emptyList(), Collections.emptyList());
        }

        // 3. 根据角色ID列表查询角色信息，提取角色码
        List<String> roleCodes = roleRepository.findByIds(roleIds)
                .stream()
                .map(role -> role.getRoleCode())
                .collect(Collectors.toList());

        // 4. 根据角色ID列表查询权限ID列表
        List<Long> permissionIds = rolePermissionRepository.findPermissionIdsByRoleIds(roleIds);

        // 5. 根据权限ID列表查询权限信息，提取权限码
        List<String> permissionCodes = Collections.emptyList();
        if (!permissionIds.isEmpty()) {
            permissionCodes = permissionRepository.findByIds(permissionIds)
                    .stream()
                    .map(permission -> permission.getPermissionCode())
                    .collect(Collectors.toList());
        }

        // 6. 使用 Assembler 转换为 DTO 并返回
        return userAssembler.toUserInfoDTO(user, roleCodes, permissionCodes);
    }
}
