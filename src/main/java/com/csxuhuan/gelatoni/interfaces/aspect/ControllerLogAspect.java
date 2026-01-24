package com.csxuhuan.gelatoni.interfaces.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Controller 层日志切面
 *
 * <p>统一记录所有 Controller 方法的请求日志，包括：
 * <ul>
 *     <li>HTTP 方法和请求路径</li>
 *     <li>请求参数摘要</li>
 *     <li>响应结果摘要</li>
 *     <li>链路追踪 ID（traceId/spanId）</li>
 *     <li>请求耗时</li>
 * </ul>
 *
 * <p>日志格式示例：
 * <pre>
 * HTTP POST /api/notice/page | args=[...] | result=BaseResponse{...} | traceId=xxx | spanId=yyy | 25ms
 * </pre>
 *
 * <p>日志输出到独立的 logger：com.csxuhuan.gelatoni.aspect.controller
 *
 * @author csxuhuan
 */
@Slf4j(topic = "com.csxuhuan.gelatoni.aspect.controller")
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * Controller 方法环绕通知
     *
     * <p>拦截所有 Controller 类的 public 方法，在方法执行前后记录日志。
     * 切点表达式匹配 interfaces.web 包下所有以 Controller 结尾的类的所有方法。
     *
     * @param joinPoint 切入点，包含目标方法的信息
     * @return 目标方法的返回值
     * @throws Throwable 目标方法抛出的异常
     */
    @Around("execution(* com.csxuhuan.gelatoni.interfaces.web..*Controller.*(..))")
    public Object logDigest(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result;

        // 获取 HTTP 请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 获取入参摘要
        Object[] args = joinPoint.getArgs();
        String argsStr = Arrays.toString(args);

        // 执行目标方法
        result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        // 获取 Spring Cloud Sleuth 的链路追踪 ID
        String traceId = org.slf4j.MDC.get("traceId");
        String spanId = org.slf4j.MDC.get("spanId");

        // 记录请求日志
        log.info("HTTP {} {} | args={} | result={} | traceId={} | spanId={} | {}ms",
                method, uri, argsStr, summarize(result), traceId, spanId, duration);

        return result;
    }

    /**
     * 生成对象的摘要字符串
     *
     * <p>将对象转为字符串，超过 100 字符时截断并添加省略号。
     * 用于避免日志中打印过长的响应内容。
     *
     * @param obj 要摘要的对象
     * @return 摘要字符串，最长 103 字符（100 + "..."）
     */
    private String summarize(Object obj) {
        if (obj == null) return "null";
        String str = obj.toString();
        return str.length() > 100 ? str.substring(0, 100) + "..." : str;
    }
}
