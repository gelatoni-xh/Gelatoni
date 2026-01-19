package com.csxuhuan.gelatoni.interfaces.web.common;

/**
 * 响应基类
 * @param <T> 前端展示用 DTO
 */
public class BaseResponse<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private String statusCode;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 创建响应
     *
     * @param success 是否成功
     * @param statusCode 响应码
     * @param data 响应数据
     * @param message 错误信息
     */
    public BaseResponse(boolean success, String statusCode, T data, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }


    /**
     * 创建成功响应
     *
     * @param data 响应数据
     * @param <T> 前端展示用 DTO
     * @return 响应
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, ResultCode.SUCCESS.getCode(), data, null);
    }

    /**
     * 创建错误响应
     *
     * @param resultCode 响应码
     * @param message 错误信息
     * @param <T> 前端展示用 DTO
     * @return 错误响应
     */
    public static <T> BaseResponse<T> error(ResultCode resultCode, String message) {
        return new BaseResponse<>(false, resultCode.getCode(), null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
