package com.csxuhuan.gelatoni.interfaces.web.common;

/**
 * 响应码枚举
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS("200", "成功"),

    /**
     * 系统异常
     */
    FAIL("500", "系统异常")
    ;

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
