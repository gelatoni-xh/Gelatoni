package com.csxuhuan.gelatoni.application.exception;

import com.csxuhuan.gelatoni.interfaces.web.common.ResultCode;

public class BizException extends RuntimeException {

    private final BizErrorCode errorCode;

    public BizException(BizErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizErrorCode getErrorCode() {
        return errorCode;
    }
}

