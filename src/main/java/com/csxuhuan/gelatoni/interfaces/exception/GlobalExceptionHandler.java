package com.csxuhuan.gelatoni.interfaces.exception;

import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * <p>统一处理 Controller 层抛出的异常，将异常转换为标准的 {@link BaseResponse} 格式返回。
 * 确保客户端始终收到一致的错误响应格式。
 *
 * <p>处理的异常类型：
 * <ul>
 *     <li>{@link BizException} - 业务异常，根据错误码映射为对应的响应码</li>
 *     <li>{@link Exception} - 其他未捕获异常，统一返回系统错误</li>
 * </ul>
 *
 * @author csxuhuan
 * @see BizException
 * @see ResultCode
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * <p>将业务异常的错误码映射为 HTTP 响应码，并返回错误信息。
     * 业务异常通常是可预期的错误，如参数校验失败、业务规则不满足等。
     *
     * @param ex 业务异常
     * @return 标准错误响应
     */
    @ExceptionHandler(BizException.class)
    public BaseResponse<Void> handleBizException(BizException ex) {

        ResultCode resultCode;
        switch (ex.getErrorCode()) {
            case INVALID_PARAM:
                resultCode = ResultCode.PARAM_ERROR;
                break;
            default:
                resultCode = ResultCode.BIZ_ERROR;
        }

        return BaseResponse.error(resultCode, ex.getMessage());
    }

    /**
     * 处理未捕获的系统异常
     *
     * <p>兜底异常处理器，处理所有未被其他处理器捕获的异常。
     * 这类异常通常是非预期的系统错误，如空指针、数据库连接失败等。
     *
     * <p>注意：生产环境中应考虑隐藏敏感的异常信息，避免泄露系统细节。
     *
     * @param ex 未捕获的异常
     * @return 系统错误响应
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception ex) {
        return BaseResponse.error(
                ResultCode.SYSTEM_ERROR,
                ex.getMessage()
        );
    }
}
