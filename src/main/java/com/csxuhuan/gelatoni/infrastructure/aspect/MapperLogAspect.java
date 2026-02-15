package com.csxuhuan.gelatoni.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;

import java.util.Arrays;

/**
 * Mapper 层日志切面
 *
 * <p>统一记录所有 Mapper 相关操作的日志，包括：
 * <ul>
 *     <li>Mapper 操作类型（SELECT/INSERT/UPDATE/DELETE）</li>
 *     <li>操作的表名</li>
 *     <li>操作参数摘要</li>
 *     <li>操作结果摘要</li>
 *     <li>操作耗时</li>
 *     <li>异常信息（当操作失败时）</li>
 * </ul>
 *
 * <p>日志格式示例：
 * <pre>
 * MAPPER SELECT from user | args=[123] | result=UserDO{...} | 10ms
 * MAPPER INSERT into user | args=[UserDO{...}] | result=1 | 5ms
 * MAPPER UPDATE user | args=[UserDO{...}] | result=1 | 8ms
 * MAPPER DELETE from user | args=[123] | result=1 | 3ms
 * MAPPER SELECT from user | args=[123] | ERROR: Connection timeout | 1000ms
 * </pre>
 *
 * <p>日志输出到独立的 logger：com.csxuhuan.gelatoni.aspect.mapper
 *
 * @author csxuhuan
 */
@Slf4j(topic = "com.csxuhuan.gelatoni.aspect.mapper")
@Aspect
@Component
public class MapperLogAspect {

    /**
     * Mapper 方法环绕通知
     *
     * <p>拦截所有 Mapper 接口的方法，在方法执行前后记录日志。
     * 如果目标方法抛出异常，会记录异常信息并重新抛出异常。
     *
     * @param joinPoint 切入点，包含目标方法的信息
     * @return 目标方法的返回值
     * @throws Throwable 目标方法抛出的异常
     */
    @Around("execution(* com.csxuhuan.gelatoni.infrastructure.repository.mapper..*Mapper.*(..))")
    public Object logMapperOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 获取方法名和参数
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        Object result;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            long duration = System.currentTimeMillis() - start;

            // 获取链路追踪 ID
            String traceId = MDC.get("traceId");
            String spanId = MDC.get("spanId");

            // 根据方法名推断操作类型
            String operationType = inferOperationType(methodName);
            String tableName = inferTableName(className);

            // 记录成功日志
            log.info("MAPPER {} {} | args={} | result={} | traceId={} | spanId={} | {}ms",
                    operationType, tableName, formatArgs(args), summarize(result), traceId, spanId, duration);

            return result;
        } catch (Throwable throwable) {
            // 记录异常信息
            long duration = System.currentTimeMillis() - start;
            
            // 获取链路追踪 ID
            String traceId = MDC.get("traceId");
            String spanId = MDC.get("spanId");

            String operationType = inferOperationType(methodName);
            String tableName = inferTableName(className);

            log.error("MAPPER {} {} | args={} | ERROR: {} | traceId={} | spanId={} | {}ms",
                    operationType, tableName, formatArgs(args), throwable.getMessage(), traceId, spanId, duration);
            
            // 重新抛出异常
            throw throwable;
        }
    }

    /**
     * 根据方法名推断操作类型
     *
     * @param methodName 方法名
     * @return 操作类型（SELECT/INSERT/UPDATE/DELETE）
     */
    private String inferOperationType(String methodName) {
        if (methodName.startsWith("select") || methodName.startsWith("get") || methodName.startsWith("find")) {
            return "SELECT";
        } else if (methodName.startsWith("insert")) {
            return "INSERT";
        } else if (methodName.startsWith("update")) {
            return "UPDATE";
        } else if (methodName.startsWith("delete")) {
            return "DELETE";
        }
        return methodName.toUpperCase();
    }

    /**
     * 根据类名推断表名
     *
     * @param className Mapper 类名
     * @return 表名
     */
    private String inferTableName(String className) {
        // 移除 "Mapper" 后缀，转换为小写
        if (className.endsWith("Mapper")) {
            return className.substring(0, className.length() - 6).toLowerCase();
        }
        return className.toLowerCase();
    }

    /**
     * 格式化参数数组为字符串
     *
     * @param args 参数数组
     * @return 格式化后的字符串
     */
    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(summarize(args[i]));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 生成对象的摘要字符串
     *
     * <p>将对象转为字符串，超过 100 字符时截断并添加省略号。
     * 用于避免日志中打印过长的内容。
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
