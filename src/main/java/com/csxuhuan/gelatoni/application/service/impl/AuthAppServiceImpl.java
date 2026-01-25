package com.csxuhuan.gelatoni.application.service.impl;

import com.csxuhuan.gelatoni.application.assembler.UserAssembler;
import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import com.csxuhuan.gelatoni.application.service.AuthAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.domain.service.UserDomainService;
import com.csxuhuan.gelatoni.infrastructure.repository.PermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.RolePermissionRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.RoleRepository;
import com.csxuhuan.gelatoni.infrastructure.repository.UserRoleRepository;
import com.csxuhuan.gelatoni.infrastructure.util.JwtUtil;
import com.csxuhuan.gelatoni.infrastructure.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final JwtUtil jwtUtil;

    /**
     * 构造函数，注入依赖
     *
     * @param userDomainService         用户领域服务
     * @param userRoleRepository        用户角色关联仓储
     * @param roleRepository            角色仓储
     * @param rolePermissionRepository  角色权限关联仓储
     * @param permissionRepository      权限仓储
     * @param userAssembler             用户装配器
     * @param jwtUtil                   JWT 工具类
     */
    public AuthAppServiceImpl(UserDomainService userDomainService,
                              UserRoleRepository userRoleRepository,
                              RoleRepository roleRepository,
                              RolePermissionRepository rolePermissionRepository,
                              PermissionRepository permissionRepository,
                              UserAssembler userAssembler,
                              JwtUtil jwtUtil) {
        this.userDomainService = userDomainService;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.userAssembler = userAssembler;
        this.jwtUtil = jwtUtil;
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

        // 3-5. 根据角色ID列表获取角色码和权限码
        List<String> roleCodes = new ArrayList<>();
        List<String> permissionCodes = getRoleCodesAndPermissionsByRoleIds(roleIds, roleCodes);

        // 6. 使用 Assembler 转换为 DTO 并返回
        return userAssembler.toUserInfoDTO(user, roleCodes, permissionCodes);
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：
     * <ol>
     *     <li>验证用户凭证（用户名和密码）</li>
     *     <li>获取用户角色和权限信息</li>
     *     <li>生成 JWT Token</li>
     *     <li>返回用户信息（包含 Token）</li>
     * </ol>
     */
    @Override
    public UserInfoDTO login(String username, String password) {
        // 1. 验证用户凭证
        User user = validateUserCredentials(username, password);

        // 2. 获取用户角色和权限信息
        UserInfoDTO userInfoDTO = getUserWithRolesAndPermissions(username);

        // 3. 生成 JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 4. 设置 Token 并返回
        userInfoDTO.setToken(token);
        return userInfoDTO;
    }

    /**
     * {@inheritDoc}
     *
     * <p>实现说明：
     * <ol>
     *     <li>根据角色编码查询角色信息</li>
     *     <li>根据角色ID查询权限ID列表</li>
     *     <li>根据权限ID列表查询权限信息，提取权限码</li>
     *     <li>使用 Assembler 转换为 DTO 并返回</li>
     * </ol>
     */
    @Override
    public UserInfoDTO getUserInfoByRoleCode(String roleCode) {
        // 1. 初始化角色码列表，先加入传入的角色编码（即使查询不到，也要包含这个角色）
        List<String> roleCodes = new ArrayList<>();
        roleCodes.add(roleCode);

        // 2. 根据角色编码查询角色信息
        Role role = roleRepository.findByRoleCode(roleCode);
        if (role == null) {
            // 如果查询不到角色，只返回角色编码，权限为空
            return userAssembler.toUserInfoDTO(null, roleCodes, Collections.emptyList());
        }

        // 3-4. 根据角色ID获取权限码
        List<Long> roleIds = Collections.singletonList(role.getId());
        List<String> permissionCodes = getRoleCodesAndPermissionsByRoleIds(roleIds, roleCodes);

        // 5. 使用 Assembler 转换为 DTO 并返回（匿名用户，user 为 null）
        return userAssembler.toUserInfoDTO(null, roleCodes, permissionCodes);
    }

    /**
     * 根据角色ID列表获取角色码和权限码
     *
     * <p>这是一个公共方法，用于提取获取角色和权限的逻辑。
     *
     * @param roleIds    角色ID列表
     * @param roleCodes  用于接收角色码列表的输出参数（会被填充）
     * @return 权限码列表
     */
    private List<String> getRoleCodesAndPermissionsByRoleIds(List<Long> roleIds, List<String> roleCodes) {
        // 1. 根据角色ID列表查询角色信息，提取角色码
        List<Role> roles = roleRepository.findByIds(roleIds);
        roleCodes.addAll(roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList()));

        // 2. 根据角色ID列表查询权限ID列表
        List<Long> permissionIds = rolePermissionRepository.findPermissionIdsByRoleIds(roleIds);

        // 3. 根据权限ID列表查询权限信息，提取权限码
        List<String> permissionCodes = Collections.emptyList();
        if (!permissionIds.isEmpty()) {
            permissionCodes = permissionRepository.findByIds(permissionIds)
                    .stream()
                    .map(Permission::getPermissionCode)
                    .collect(Collectors.toList());
        }

        return permissionCodes;
    }

    /**
     * 验证用户凭证
     *
     * <p>校验用户是否存在以及密码是否正确。
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户领域对象
     * @throws RuntimeException 如果用户不存在或密码错误
     */
    private User validateUserCredentials(String username, String password) {
        // 1. 根据用户名查询用户
        User user = userDomainService.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 校验密码是否正确
        String passwordHash = user.getPasswordHash();
        if (passwordHash == null || !PasswordUtil.verifyPassword(password, passwordHash)) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }
}
