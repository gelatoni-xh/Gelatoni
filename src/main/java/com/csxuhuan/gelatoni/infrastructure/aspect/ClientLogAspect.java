package com.csxuhuan.gelatoni.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;

/**
 * 外部服务调用日志切面
 *
 * <p>统一记录 client 包下所有外部服务调用的日志，包括：
 * <ul>
 *     <li>服务名（根据类名推断，如 wikipedia）</li>
 *     <li>方法名</li>
 *     <li>调用参数摘要</li>
 *     <li>调用结果摘要</li>
 *     <li>调用耗时</li>
 *     <li>异常信息（当调用失败时）</li>
 * </ul>
 *
 * <p>日志格式示例：
 * <pre>
 * CLIENT wikipedia.fetchPage | args=[田澤廉] | result=WikiPageResult{...} | traceId=xxx | spanId=xxx | 320ms
 * CLIENT wikipedia.fetchPage | args=[田澤廉] | ERROR: Connection timeout | traceId=xxx | spanId=xxx | 5000ms
 * </pre>
 *
 * <p>日志输出到独立的 logger：com.csxuhuan.gelatoni.aspect.client
 *
 * @author csxuhuan
 */
@Slf4j(topic = "com.csxuhuan.gelatoni.aspect.client")
@Aspect
@Component
public class ClientLogAspect {

    @Around("execution(* com.csxuhuan.gelatoni.infrastructure.client..*Client.*(..))")
    public Object logClientCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String serviceName = inferServiceName(className);

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            String traceId = MDC.get("traceId");
            String spanId = MDC.get("spanId");

            log.info("CLIENT {}.{} | args={} | result={} | traceId={} | spanId={} | {}ms",
                    serviceName, methodName, formatArgs(args), summarize(result), traceId, spanId, duration);
            return result;
        } catch (Throwable throwable) {
            long duration = System.currentTimeMillis() - start;
            String traceId = MDC.get("traceId");
            String spanId = MDC.get("spanId");

            log.error("CLIENT {}.{} | args={} | ERROR: {} | traceId={} | spanId= | {}ms",
                    serviceName, methodName, formatArgs(args), throwable.getMessage(), traceId, spanId, duration);
            throw throwable;
        }
    }

    /**
     * 根据类名推断服务名，如 WikipediaClient -> wikipedia
     */
    private String inferServiceName(String className) {
        if (className.endsWith("Client")) {
            return className.substring(0, className.length() - 6).toLowerCase();
        }
        return className.toLowerCase();
    }

    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(summarize(args[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    private String summarize(Object obj) {
        if (obj == null) return "null";
        String str = obj.toString();
        return str.length() > 100 ? str.substring(0, 100) + "..." : str;
    }
}
