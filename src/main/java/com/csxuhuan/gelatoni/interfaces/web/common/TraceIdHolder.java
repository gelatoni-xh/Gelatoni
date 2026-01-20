package com.csxuhuan.gelatoni.interfaces.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
public class TraceIdHolder {

    private static Tracer tracer;

    @Autowired
    public TraceIdHolder(Tracer tracer) {
        TraceIdHolder.tracer = tracer;
    }

    /**
     * 获取当前 traceId
     */
    public static String get() {
        Span span = tracer.currentSpan();
        return span == null ? null : span.context().traceId();
    }
}
