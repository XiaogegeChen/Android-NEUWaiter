package com.neuwljs.wallsmalltwo.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * 服务器返回数据的统一封装
 * @param <T>
 */
public class Response<T> {

    /**
     * 错误码
     */
    @SerializedName("error_code")
    private String errorCode;

    /**
     * 错误原因
     */
    @SerializedName("reason")
    private String reason;

    /**
     * 数据
     */
    @SerializedName("result")
    private T result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
