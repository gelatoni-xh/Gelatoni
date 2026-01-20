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
     * 参数错误
     */
    PARAM_ERROR("400", "参数错误"),

    /**
     * 业务处理失败
     */
    BIZ_ERROR("1000", "业务处理失败"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("500", "系统异常");
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
