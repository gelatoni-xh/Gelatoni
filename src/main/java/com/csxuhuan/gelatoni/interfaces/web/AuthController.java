package com.csxuhuan.gelatoni.interfaces.web;

import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import com.csxuhuan.gelatoni.application.service.AuthAppService;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.request.LoginRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证控制器
 *
 * <p>提供用户认证相关的 RESTful API 接口，包括：
 * <ul>
 *     <li>用户登录</li>
 * </ul>
 *
 * <p>接口路径前缀：/api/auth
 *
 * @author csxuhuan
 * @see AuthAppService
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /** 匿名角色编码 */
    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    private final AuthAppService authAppService;

    /**
     * 构造函数，注入依赖服务
     *
     * @param authAppService 认证应用服务
     */
    public AuthController(AuthAppService authAppService) {
        this.authAppService = authAppService;
    }

    /**
     * 用户登录
     *
     * <p>接收用户名和密码，完成登录验证，返回用户信息和 JWT Token。
     * 此接口不需要认证。
     *
     * <p>返回信息包括：
     * <ul>
     *     <li>用户基本信息</li>
     *     <li>用户拥有的所有角色编码列表</li>
     *     <li>用户拥有的所有权限编码列表</li>
     *     <li>JWT Token（用于后续接口认证）</li>
     * </ul>
     *
     * @param request 登录请求，包含 username（用户名）和 password（密码）
     * @return 用户信息结果，包含用户 DTO、角色码列表、权限码列表、Token
     */
    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserInfoDTO> login(@Valid @RequestBody LoginRequest request) {
        try {
            UserInfoDTO userInfo = authAppService.login(
                    request.getUsername(),
                    request.getPassword()
            );
            return BaseResponse.success(userInfo);
        } catch (RuntimeException e) {
            return BaseResponse.error(ResultCode.BIZ_ERROR, e.getMessage());
        }
    }

    /**
     * 获取匿名用户信息
     *
     * <p>未登录前查看所拥有角色的接口，返回匿名用户的角色和权限信息。
     * 此接口不需要认证。
     *
     * <p>返回信息包括：
     * <ul>
     *     <li>用户基本信息（匿名用户时为 null）</li>
     *     <li>角色编码列表（包含 ROLE_ANONYMOUS）</li>
     *     <li>权限编码列表（ROLE_ANONYMOUS 角色拥有的所有权限）</li>
     * </ul>
     *
     * @return 用户信息结果，包含用户 DTO（null）、角色码列表、权限码列表
     */
    @GetMapping(value = "/anonymous",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<UserInfoDTO> getAnonymousUserInfo() {
        try {
            UserInfoDTO userInfo = authAppService.getUserInfoByRoleCode(ROLE_ANONYMOUS);
            return BaseResponse.success(userInfo);
        } catch (RuntimeException e) {
            return BaseResponse.error(ResultCode.BIZ_ERROR, e.getMessage());
        }
    }
}
