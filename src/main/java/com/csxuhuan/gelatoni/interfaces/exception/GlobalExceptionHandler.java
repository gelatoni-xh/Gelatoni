package com.csxuhuan.gelatoni.interfaces.exception;

import com.csxuhuan.gelatoni.application.exception.BizException;
import com.csxuhuan.gelatoni.interfaces.web.common.BaseResponse;
import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception ex) {
        return BaseResponse.error(
                ResultCode.SYSTEM_ERROR,
                ex.getMessage()
        );
    }
}
