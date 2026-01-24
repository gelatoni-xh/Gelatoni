package com.csxuhuan.gelatoni.interfaces.config;

import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证拦截器
 *
 * <p>实现基于 Bearer Token 的简单认证机制。
 * 当请求的 Controller 方法标记了 {@link AuthCheck} 注解时，
 * 此拦截器会验证请求头中的 Authorization 是否携带正确的 Token。
 *
 * <p>Token 验证规则：
 * <ul>
 *     <li>请求头格式：Authorization: Bearer {token}</li>
 *     <li>Token 值从环境变量 API_TOKEN 读取</li>
 *     <li>验证失败返回 HTTP 401 状态码和标准错误响应</li>
 * </ul>
 *
 * <p>注意：这是一个简化的认证实现，适用于内部 API 或简单场景。
 * 生产环境建议使用 JWT 或 OAuth2 等完整认证方案。
 *
 * @author csxuhuan
 * @see AuthCheck
 * @see WebConfig#addInterceptors
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * API 访问令牌，从环境变量 API_TOKEN 读取
     */
    @Value("${API_TOKEN}")
    private String fixedToken;

    /**
     * 请求预处理，在 Controller 方法执行前进行认证检查
     *
     * <p>检查流程：
     * <ol>
     *     <li>判断是否为 Controller 方法请求</li>
     *     <li>检查方法是否标记了 @AuthCheck 注解</li>
     *     <li>验证 Authorization 请求头中的 Bearer Token</li>
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
        if (method.getMethod().isAnnotationPresent(AuthCheck.class)) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.equals("Bearer " + fixedToken)) {
                // 记录认证失败日志（生产环境注意脱敏）
                log.error("认证失败 - authHeader: {}, fixedToken: {}", authHeader, fixedToken);

                // 返回统一的 JSON 错误响应
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                BaseResponse<Object> errorResponse =
                        BaseResponse.error(ResultCode.UNAUTHORIZED, "Unauthorized: Invalid or missing token");
                response.getWriter().write(toJson(errorResponse));
                return false;
            }
        }
        return true;
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
