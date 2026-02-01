package com.csxuhuan.gelatoni.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;

import java.time.Duration;

/**
 * Redis 操作日志切面
 *
 * <p>统一记录所有 Redis 相关操作的日志，包括：
 * <ul>
 *     <li>Redis 操作类型（GET/SET/DELETE/EXISTS/INCREMENT）</li>
 *     <li>操作的键名</li>
 *     <li>操作参数（如过期时间、增量值等）</li>
 *     <li>操作结果摘要</li>
 *     <li>操作耗时</li>
 *     <li>异常信息（当操作失败时）</li>
 * </ul>
 *
 * <p>日志格式示例：
 * <pre>
 * REDIS SET key=user:123 | value=UserDTO{...} | ttl=30m | 5ms
 * REDIS GET key=user:123 | result=UserDTO{...} | 3ms
 * REDIS DELETE key=user:123 | result=true | 2ms
 * REDIS EXISTS key=user:123 | result=false | 1ms
 * REDIS INCREMENT key=counter:login | delta=1 | ttl=1h | result=42 | 4ms
 * REDIS GET key=user:123 | ERROR: Connection timeout | 1000ms
 * </pre>
 *
 * <p>日志输出到独立的 logger：com.csxuhuan.gelatoni.aspect.redis
 *
 * @author csxuhuan
 */
@Slf4j(topic = "com.csxuhuan.gelatoni.aspect.redis")
@Aspect
@Component
public class RedisLogAspect {

    /**
     * Redis Client 方法环绕通知
     *
     * <p>拦截 RedisClient 类的所有公共方法，在方法执行前后记录日志。
     * 如果目标方法抛出异常，会记录异常信息并重新抛出异常。
     *
     * @param joinPoint 切入点，包含目标方法的信息
     * @return 目标方法的返回值
     * @throws Throwable 目标方法抛出的异常
     */
    @Around("execution(* com.csxuhuan.gelatoni.infrastructure.redis.RedisClient.*(..))")
    public Object logRedisOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 获取方法名和参数
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Object result;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            
            long duration = System.currentTimeMillis() - start;

            // 根据不同方法记录不同的日志格式
            logRedisSuccess(methodName, args, result, duration);

            return result;
        } catch (Throwable throwable) {
            // 记录异常信息
            long duration = System.currentTimeMillis() - start;
            logRedisError(methodName, args, throwable, duration);
            
            // 重新抛出异常
            throw throwable;
        }
    }

    /**
     * 记录 Redis 操作成功的日志
     *
     * @param methodName 方法名
     * @param args 方法参数
     * @param result 方法返回值
     * @param duration 操作耗时（毫秒）
     */
    private void logRedisSuccess(String methodName, Object[] args, Object result, long duration) {
        String traceId = MDC.get("traceId");
        String spanId = MDC.get("spanId");
        switch (methodName) {
            case "set":
                logSetOperation(args, duration, traceId, spanId);
                break;
            case "get":
                logGetOperation(args, result, duration, traceId, spanId);
                break;
            case "delete":
                logDeleteOperation(args, result, duration, traceId, spanId);
                break;
            case "exists":
                logExistsOperation(args, result, duration, traceId, spanId);
                break;
            case "increment":
                logIncrementOperation(args, result, duration, traceId, spanId);
                break;
            default:
                // 其他方法使用通用格式
                log.info("REDIS {} | args={} | result={} | traceId={} | spanId={} | {}ms", 
                        methodName.toUpperCase(), formatArgs(args), summarize(result), traceId, spanId, duration);
        }
    }

    /**
     * 记录 Redis 操作失败的日志
     *
     * @param methodName 方法名
     * @param args 方法参数
     * @param throwable 异常对象
     * @param duration 操作耗时（毫秒）
     */
    private void logRedisError(String methodName, Object[] args, Throwable throwable, long duration) {
        String traceId = MDC.get("traceId");
        String spanId = MDC.get("spanId");
        log.error("REDIS {} | args={} | ERROR: {} | traceId={} | spanId={} | {}ms",
                methodName.toUpperCase(), formatArgs(args), throwable.getMessage(), traceId, spanId, duration);
    }

    /**
     * 记录 SET 操作日志
     */
    private void logSetOperation(Object[] args, long duration, String traceId, String spanId) {
        if (args.length >= 2) {
            String key = String.valueOf(args[0]);
            Object value = args[1];
            String ttlInfo = args.length > 2 && args[2] != null ? 
                " | ttl=" + formatDuration((Duration) args[2]) : "";
            
            log.info("REDIS SET key={} | value={}{} | traceId={} | spanId={} | {}ms", 
                    key, summarize(value), ttlInfo, traceId, spanId, duration);
        }
    }

    /**
     * 记录 GET 操作日志
     */
    private void logGetOperation(Object[] args, Object result, long duration, String traceId, String spanId) {
        if (args.length >= 1) {
            String key = String.valueOf(args[0]);
            log.info("REDIS GET key={} | result={} | traceId={} | spanId={} | {}ms", 
                    key, summarize(result), traceId, spanId, duration);
        }
    }

    /**
     * 记录 DELETE 操作日志
     */
    private void logDeleteOperation(Object[] args, Object result, long duration, String traceId, String spanId) {
        if (args.length >= 1) {
            String key = String.valueOf(args[0]);
            log.info("REDIS DELETE key={} | result={} | traceId={} | spanId={} | {}ms", 
                    key, result, traceId, spanId, duration);
        }
    }

    /**
     * 记录 EXISTS 操作日志
     */
    private void logExistsOperation(Object[] args, Object result, long duration, String traceId, String spanId) {
        if (args.length >= 1) {
            String key = String.valueOf(args[0]);
            log.info("REDIS EXISTS key={} | result={} | traceId={} | spanId={} | {}ms", 
                    key, result, traceId, spanId, duration);
        }
    }

    /**
     * 记录 INCREMENT 操作日志
     */
    private void logIncrementOperation(Object[] args, Object result, long duration, String traceId, String spanId) {
        if (args.length >= 2) {
            String key = String.valueOf(args[0]);
            long delta = (Long) args[1];
            String ttlInfo = args.length > 2 && args[2] != null ? 
                " | ttl=" + formatDuration((Duration) args[2]) : "";
            
            log.info("REDIS INCREMENT key={} | delta={}{} | result={} | traceId={} | spanId={} | {}ms", 
                    key, delta, ttlInfo, result, traceId, spanId, duration);
        }
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
     * 格式化 Duration 对象为易读的字符串
     *
     * @param duration Duration 对象
     * @return 格式化后的字符串（如 "30m", "1h", "2d"）
     */
    private String formatDuration(Duration duration) {
        if (duration == null) return "null";
        
        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "s";
        } else if (seconds < 3600) {
            return (seconds / 60) + "m";
        } else if (seconds < 86400) {
            return (seconds / 3600) + "h";
        } else {
            return (seconds / 86400) + "d";
        }
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