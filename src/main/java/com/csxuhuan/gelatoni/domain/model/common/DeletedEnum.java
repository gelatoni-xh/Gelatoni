package com.csxuhuan.gelatoni.domain.model.common;

/**
 * 逻辑删除枚举
 */
public enum DeletedEnum {
    /**
     * 未删除
     */
    NOT_DELETED(Boolean.FALSE),

    /**
     * 已删除
     */
    DELETED(Boolean.TRUE);

    private final Boolean value;

    DeletedEnum(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
