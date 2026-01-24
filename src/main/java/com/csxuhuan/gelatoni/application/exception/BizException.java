package com.csxuhuan.gelatoni.application.exception;

/**
 * 业务异常
 *
 * <p>表示在业务处理过程中发生的可预期异常，如：
 * <ul>
 *     <li>参数校验失败</li>
 *     <li>业务规则不满足</li>
 *     <li>资源不存在</li>
 *     <li>操作不允许</li>
 * </ul>
 *
 * <p>与系统异常的区别：
 * <ul>
 *     <li>BizException - 业务层面的错误，用户可理解和处理</li>
 *     <li>RuntimeException - 系统层面的错误，如数据库连接失败</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * if (param == null) {
 *     throw new BizException(BizErrorCode.INVALID_PARAM, "参数不能为空");
 * }
 * </pre>
 *
 * @author csxuhuan
 * @see BizErrorCode
 * @see com.csxuhuan.gelatoni.interfaces.exception.GlobalExceptionHandler
 */
public class BizException extends RuntimeException {

    /**
     * 业务错误码
     */
    private final BizErrorCode errorCode;

    /**
     * 构造业务异常
     *
     * @param errorCode 业务错误码
     * @param message   错误信息，向用户展示的具体说明
     */
    public BizException(BizErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 获取业务错误码
     *
     * @return 错误码枚举
     */
    public BizErrorCode getErrorCode() {
        return errorCode;
    }
}

