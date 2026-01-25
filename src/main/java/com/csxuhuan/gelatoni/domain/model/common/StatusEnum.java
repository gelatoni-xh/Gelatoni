package com.csxuhuan.gelatoni.domain.model.common;

/**
 * 状态枚举
 */
public enum StatusEnum {
    /**
     * 禁用
     */
    DISABLED((byte) 0),

    /**
     * 启用
     */
    ENABLED((byte) 1);

    private final Byte value;

    StatusEnum(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
