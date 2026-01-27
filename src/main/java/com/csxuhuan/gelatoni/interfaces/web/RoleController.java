package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.assembler.RoleAssembler;
import com.csxuhuan.gelatoni.application.dto.RoleDTO;
import com.csxuhuan.gelatoni.application.service.RoleAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Role;
import com.csxuhuan.gelatoni.domain.query.RoleCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.interfaces.web.request.RoleCreateOrUpdateRequest;
import com.csxuhuan.gelatoni.interfaces.web.request.RolePermissionAssignRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理控制器
 *
 * <p>提供角色管理相关的 RESTful API，包括：
 * <ul>
 *     <li>查询角色列表</li>
 *     <li>创建/编辑角色</li>
 *     <li>给角色配置权限</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/role
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleAppService roleAppService;
    private final RoleAssembler roleAssembler = new RoleAssembler();

    public RoleController(RoleAppService roleAppService) {
        this.roleAppService = roleAppService;
    }

    /**
     * 查询角色列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<RoleDTO>> list() {
        List<Role> roles = roleAppService.findAllRoles();
        return BaseResponse.success(roleAssembler.toDTOList(roles));
    }

    /**
     * 创建角色
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody RoleCreateOrUpdateRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        RoleCreateOrUpdateQuery query = roleAssembler.toDomainQuery(request);
        int result = roleAppService.createRole(query, operator);
        return BaseResponse.success(result);
    }

    /**
     * 更新角色
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody RoleCreateOrUpdateRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        if (request.getId() == null) {
            return BaseResponse.error(ResultCode.PARAM_ERROR, "角色ID不能为空");
        }
        RoleCreateOrUpdateQuery query = roleAssembler.toDomainQuery(request);
        int result = roleAppService.updateRole(query, operator);
        return BaseResponse.success(result);
    }

    /**
     * 给角色配置权限（覆盖式）
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/assign-permission",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> assignPermission(@Valid @RequestBody RolePermissionAssignRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        int result = roleAppService.assignPermissions(request.getRoleId(), request.getPermissionIds(), operator);
        return BaseResponse.success(result);
    }

    /**
     * 根据角色ID获取权限码列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @GetMapping(value = "/permissions-by-role/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<String>> getPermissionCodesByRoleId(@PathVariable Long roleId) {
        List<String> permissionCodes = roleAppService.getPermissionCodesByRoleId(roleId);
        return BaseResponse.success(permissionCodes);
    }
}
