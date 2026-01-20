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

@Slf4j(topic = "com.csxuhuan.gelatoni.aspect.controller")
@Aspect
@Component
public class ControllerLogAspect {

    @Around("execution(* com.csxuhuan.gelatoni.interfaces.web..*Controller.*(..))")
    public Object logDigest(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result;

        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 入参摘要
        Object[] args = joinPoint.getArgs();
        String argsStr = Arrays.toString(args);

        // 执行方法
        result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        // Sleuth TraceId / SpanId
        String traceId = org.slf4j.MDC.get("traceId");
        String spanId = org.slf4j.MDC.get("spanId");

        // 日志打印
        log.info("HTTP {} {} | args={} | result={} | traceId={} | spanId={} | {}ms",
                method, uri, argsStr, summarize(result), traceId, spanId, duration);

        return result;
    }

    // 简单返回值摘要函数
    private String summarize(Object obj) {
        if (obj == null) return "null";
        String str = obj.toString();
        return str.length() > 100 ? str.substring(0, 100) + "..." : str;
    }
}
