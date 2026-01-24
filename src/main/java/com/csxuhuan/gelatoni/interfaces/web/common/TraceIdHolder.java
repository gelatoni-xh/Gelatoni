package com.csxuhuan.gelatoni.interfaces.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

/**
 * 链路追踪 ID 持有器
 *
 * <p>封装 Spring Cloud Sleuth 的 Tracer，提供静态方法获取当前请求的 traceId。
 * 用于在响应中返回链路追踪 ID，便于客户端定位问题和追踪请求。
 *
 * <p>使用示例：
 * <pre>
 * String traceId = TraceIdHolder.get();
 * // traceId 格式类似：64位十六进制字符串
 * </pre>
 *
 * <p>注意：此类使用静态字段持有 Tracer 实例，通过构造函数注入。
 * 这是一种特殊的模式，用于在静态上下文中访问 Spring Bean。
 *
 * @author csxuhuan
 * @see org.springframework.cloud.sleuth.Tracer
 */
@Component
public class TraceIdHolder {

    /**
     * Sleuth 追踪器，通过构造函数注入后赋值给静态字段
     */
    private static Tracer tracer;

    /**
     * 构造函数，注入 Tracer 并赋值给静态字段
     *
     * @param tracer Spring Cloud Sleuth 追踪器
     */
    @Autowired
    public TraceIdHolder(Tracer tracer) {
        TraceIdHolder.tracer = tracer;
    }

    /**
     * 获取当前请求的链路追踪 ID
     *
     * <p>从当前 Span 上下文中提取 traceId。如果当前没有活跃的 Span（如非 HTTP 请求线程），
     * 返回 null。
     *
     * @return 当前请求的 traceId，如果不存在则返回 null
     */
    public static String get() {
        Span span = tracer.currentSpan();
        return span == null ? null : span.context().traceId();
    }
}
