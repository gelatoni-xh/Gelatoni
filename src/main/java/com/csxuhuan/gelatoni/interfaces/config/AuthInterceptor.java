package com.csxuhuan.gelatoni.interfaces.config;

import com.csxuhuan.gelatoni.application.dto.UserInfoDTO;
import com.csxuhuan.gelatoni.application.service.AuthAppService;
import com.csxuhuan.gelatoni.infrastructure.util.JwtUtil;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import com.csxuhuan.gelatoni.interfaces.web.common.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 认证拦截器
 *
 * <p>实现基于 JWT Token 的认证和权限检查机制。
 * 当请求的 Controller 方法标记了 {@link AuthCheck} 注解时，
 * 此拦截器会验证请求头中的 Authorization 是否携带有效的 JWT Token，
 * 并根据注解中指定的权限编码进行权限检查。
 *
 * <p>认证和权限检查规则：
 * <ul>
 *     <li>请求头格式：Authorization: Bearer {token}</li>
 *     <li>如果注解指定了权限编码，会先检查该权限对应的角色是否为未登录权限（匿名用户权限）</li>
 *     <li>如果是未登录权限，可以不传 Token 直接放行</li>
 *     <li>如果不是未登录权限，必须携带有效的 Token</li>
 *     <li>Token 验证失败返回 HTTP 401 状态码</li>
 *     <li>权限验证失败返回 HTTP 401 状态码</li>
 * </ul>
 *
 * <p>用户信息存储：
 * <ul>
 *     <li>验证通过后，会将用户信息存储到 {@link UserHolder} 中</li>
 *     <li>请求处理完成后，会自动清理 {@link UserHolder} 中的数据</li>
 * </ul>
 *
 * @author csxuhuan
 * @see AuthCheck
 * @see UserHolder
 * @see WebConfig#addInterceptors
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthAppService authAppService;
    private final JwtUtil jwtUtil;

    /**
     * 构造函数，注入依赖
     *
     * @param authAppService 认证应用服务
     * @param jwtUtil        JWT 工具类
     */
    public AuthInterceptor(AuthAppService authAppService, JwtUtil jwtUtil) {
        this.authAppService = authAppService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 请求预处理，在 Controller 方法执行前进行认证和权限检查
     *
     * <p>检查流程：
     * <ol>
     *     <li>判断是否为 Controller 方法请求</li>
     *     <li>检查方法是否标记了 @AuthCheck 注解</li>
     *     <li>如果注解指定了权限编码，先检查是否为未登录权限（匿名用户权限）</li>
     *     <li>如果是未登录权限，直接放行</li>
     *     <li>如果不是未登录权限，验证 Authorization 请求头中的 Bearer Token</li>
     *     <li>验证 Token 有效性，并获取用户信息</li>
     *     <li>检查用户是否拥有指定权限</li>
     *     <li>将用户信息存储到 UserHolder</li>
     * </ol>
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器对象
     * @return true 表示放行，false 表示拦截
     * @throws IOException 写入响应时可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        // 非 Controller 请求（如静态资源）直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;

        // 检查方法是否有 @AuthCheck 注解
        if (!method.getMethod().isAnnotationPresent(AuthCheck.class)) {
            return true;
        }

        AuthCheck authCheck = method.getMethod().getAnnotation(AuthCheck.class);
        String permissionCode = authCheck.permissionCode();

        // 如果指定了权限编码，先检查是否为未登录权限（匿名用户权限）
        if (permissionCode != null && !permissionCode.isEmpty()) {
            UserInfoDTO roleInfo = authAppService.getUserInfoByRoleCode(permissionCode);
            // 如果该权限对应的角色是未登录权限（user 为 null），则可以不传 Token 直接放行
            if (roleInfo != null && roleInfo.getUser() == null) {
                // 未登录权限，直接放行，不设置 UserHolder
                return true;
            }
        }

        // 获取 Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("认证失败 - 缺少 Token 或格式错误");
            return sendUnauthorizedResponse(response, "Unauthorized: Missing or invalid token format");
        }

        String token = authHeader.substring(7); // 移除 "Bearer " 前缀

        // 验证 Token 有效性
        if (!jwtUtil.validateToken(token)) {
            log.error("认证失败 - Token 无效或已过期");
            return sendUnauthorizedResponse(response, "Unauthorized: Invalid or expired token");
        }

        // 从 Token 中获取用户名
        String username;
        try {
            username = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            log.error("认证失败 - 解析 Token 失败: {}", e.getMessage());
            return sendUnauthorizedResponse(response, "Unauthorized: Failed to parse token");
        }

        // 获取用户信息（包括角色和权限）
        UserInfoDTO userInfo;
        try {
            userInfo = authAppService.getUserWithRolesAndPermissions(username);
        } catch (Exception e) {
            log.error("认证失败 - 获取用户信息失败: {}", e.getMessage());
            return sendUnauthorizedResponse(response, "Unauthorized: Failed to get user information");
        }

        // 如果指定了权限编码，检查用户是否拥有该权限
        if (permissionCode != null && !permissionCode.isEmpty()) {
            List<String> permissionCodes = userInfo.getPermissionCodes();
            if (permissionCodes == null || !permissionCodes.contains(permissionCode)) {
                log.error("权限验证失败 - 用户 {} 没有权限 {}", username, permissionCode);
                return sendUnauthorizedResponse(response, "Unauthorized: Insufficient permissions");
            }
        }

        // 检查用户信息是否完整
        if (userInfo.getUser() == null || userInfo.getUser().getId() == null) {
            log.error("认证失败 - 用户信息不完整，用户 {} 的User对象或ID为null", username);
            return sendUnauthorizedResponse(response, "Unauthorized: User information is incomplete");
        }

        // 将用户信息存储到 UserHolder
        UserHolder.set(
                userInfo.getUser().getId(),
                username,
                userInfo.getRoleCodes(),
                userInfo.getPermissionCodes()
        );

        return true;
    }

    /**
     * 请求处理完成后清理 UserHolder
     *
     * @param request   HTTP 请求
     * @param response  HTTP 响应
     * @param handler   处理器对象
     * @param ex        异常对象（如果有）
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 清理 UserHolder，避免内存泄漏
        UserHolder.clear();
    }

    /**
     * 发送未授权响应
     *
     * @param response HTTP 响应
     * @param message  错误消息
     * @return false 表示拦截请求
     * @throws IOException 写入响应时可能抛出的异常
     */
    private boolean sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        BaseResponse<Object> errorResponse = BaseResponse.error(ResultCode.UNAUTHORIZED, message);
        response.getWriter().write(toJson(errorResponse));
        return false;
    }

    /**
     * 将响应对象序列化为 JSON 字符串
     *
     * @param response 响应对象
     * @return JSON 字符串
     */
    private String toJson(BaseResponse<?> response) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(response);
        } catch (Exception e) {
            // 序列化失败时返回硬编码的错误响应
            return "{\"success\":false,\"statusCode\":\"500\",\"data\":null,\"message\":\"序列化失败\"}";
        }
    }
}
