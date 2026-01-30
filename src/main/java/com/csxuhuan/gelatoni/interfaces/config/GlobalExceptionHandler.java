package com.csxuhuan.gelatoni.interfaces.config;

import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * <p>统一处理系统中未被捕获的异常，记录错误日志并返回标准错误响应。
 * 所有 Controller 层未处理的异常都会被此处理器捕获。
 *
 * <p>记录信息包括：
 * <ul>
 *     <li>异常类型和消息</li>
 *     <li>请求 URL 和方法</li>
 *     <li>异常堆栈信息</li>
 *     <li>请求参数（从 ControllerLogAspect 中获取）</li>
 * </ul>
 *
 * @author csxuhuan
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有未被捕获的异常
     *
     * <p>记录详细的错误信息，包括请求上下文和异常堆栈，
     * 然后返回标准的错误响应给客户端。
     *
     * @param request HTTP 请求对象，用于获取请求上下文信息
     * @param e       捕获到的异常对象
     * @return 统一错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(HttpServletRequest request, Exception e) {
        // 记录详细的错误信息
        log.error("系统异常 - URL: {} {}, Method: {}, User-Agent: {}, Error: {}", 
                 request.getMethod(), request.getRequestURI(), 
                 request.getMethod(), 
                 request.getHeader("User-Agent"), 
                 e.getMessage(), e);
        
        // 返回标准错误响应
        BaseResponse<Object> errorResponse = BaseResponse.error(
            ResultCode.SYSTEM_ERROR,
            "系统内部错误"
        );
        
        return ResponseEntity.status(500).body(errorResponse);
    }
}