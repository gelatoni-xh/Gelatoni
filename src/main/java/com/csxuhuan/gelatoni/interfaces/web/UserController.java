package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.assembler.UserAssembler;
import com.csxuhuan.gelatoni.application.dto.UserDTO;
import com.csxuhuan.gelatoni.application.service.UserAppService;
import com.csxuhuan.gelatoni.domain.model.entity.User;
import com.csxuhuan.gelatoni.interfaces.config.AuthCheck;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.PermissionConstants;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import com.csxuhuan.gelatoni.interfaces.web.request.UserRoleAssignRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理控制器
 *
 * <p>提供用户管理相关的 RESTful API，包括：
 * <ul>
 *     <li>查询用户列表</li>
 *     <li>给用户分配角色</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/user
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserAppService userAppService;
    private final UserAssembler userAssembler;

    public UserController(UserAppService userAppService, UserAssembler userAssembler) {
        this.userAppService = userAppService;
        this.userAssembler = userAssembler;
    }

    /**
     * 查询用户列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<UserDTO>> list() {
        List<User> users = userAppService.findAllUsers();
        List<UserDTO> dtoList = userAssembler.toDTOList(users);
        return BaseResponse.success(dtoList);
    }

    /**
     * 给用户分配角色（覆盖式）
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @PostMapping(value = "/assign-role",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Integer> assignRole(@Valid @RequestBody UserRoleAssignRequest request) {
        Long operator = UserHolder.getUserId();
        if (operator == null) {
            return BaseResponse.error(ResultCode.UNAUTHORIZED, "用户信息不存在，请重新登录");
        }
        int result = userAppService.assignRoles(request.getUserId(), request.getRoleIds(), operator);
        return BaseResponse.success(result);
    }

    /**
     * 根据用户ID获取角色码列表
     */
    @AuthCheck(permissionCode = PermissionConstants.PERM_USER_PERMISSION_MGMT)
    @GetMapping(value = "/roles-by-user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<String>> getRoleCodesByUserId(@PathVariable Long userId) {
        List<String> roleCodes = userAppService.getRoleCodesByUserId(userId);
        return BaseResponse.success(roleCodes);
    }
}
