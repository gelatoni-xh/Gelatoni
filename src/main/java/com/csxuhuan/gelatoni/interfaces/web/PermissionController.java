package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.assembler.PermissionAssembler;
import com.csxuhuan.gelatoni.application.dto.PermissionDTO;
import com.csxuhuan.gelatoni.application.service.PermissionAppService;
import com.csxuhuan.gelatoni.domain.model.entity.Permission;
import com.csxuhuan.gelatoni.domain.query.PermissionCreateOrUpdateQuery;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.interfaces.web.request.PermissionCreateOrUpdateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限管理控制器
 *
 * <p>提供权限管理相关的 RESTful API，包括：
 * <ul>
 *     <li>查询权限列表</li>
 *     <li>新增/编辑权限</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/permission
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionAppService permissionAppService;
    private final PermissionAssembler permissionAssembler = new PermissionAssembler();

    public PermissionController(PermissionAppService permissionAppService) {
        this.permissionAppService = permissionAppService;
    }

    /**
     * 查询权限列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<PermissionDTO>> list() {
        List<Permission> permissions = permissionAppService.findAllPermissions();
        return BaseResponse.success(permissionAssembler.toDTOList(permissions));
    }

    /**
     * 创建权限
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> create(@Valid @RequestBody PermissionCreateOrUpdateRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        PermissionCreateOrUpdateQuery query = permissionAssembler.toDomainQuery(request);
        int result = permissionAppService.createPermission(query, operator);
        return BaseResponse.success(result);
    }

    /**
     * 更新权限
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> update(@Valid @RequestBody PermissionCreateOrUpdateRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        if (request.getId() == null) {
            return BaseResponse.error(ResultCode.PARAM_ERROR, "权限ID不能为空");
        }
        PermissionCreateOrUpdateQuery query = permissionAssembler.toDomainQuery(request);
        int result = permissionAppService.updatePermission(query, operator);
        return BaseResponse.success(result);
    }
}
