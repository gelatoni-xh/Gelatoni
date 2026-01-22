package com.csxuhuan.gelatoni.interfaces.config;

import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    // 从环境变量读取
    @Value("${API_TOKEN}")
    private String fixedToken;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true; // 非Controller请求直接放行
        }

        HandlerMethod method = (HandlerMethod) handler;

        // 检查方法是否有 @AuthCheck
        if (method.getMethod().isAnnotationPresent(AuthCheck.class)) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.equals("Bearer " + fixedToken)) {
                // 返回统一 BaseResponse JSON
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

    // 简单 JSON 序列化（可以用你项目已有工具，比如 Jackson）
    private String toJson(BaseResponse<?> response) {
        // 如果你项目引入了 Jackson ObjectMapper，可以直接用
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(response);
        } catch (Exception e) {
            return "{\"success\":false,\"statusCode\":\"500\",\"data\":null,\"message\":\"序列化失败\"}";
        }
    }
}
