package com.ruyuan2020.im.common.core.util;


import com.ruyuan2020.im.common.core.domain.JsonResult;

public class ResultHelper {

    public static <T> JsonResult<T> ok() {
        JsonResult<T> result = getResult(null, null, null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> JsonResult<T> ok(String message, T data) {
        JsonResult<T> result = getResult(message, null, data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = getResult(null, null, data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> JsonResult<T> fail(String errorCode, String errorMessage) {
        JsonResult<T> result = getResult(errorCode, errorMessage, null);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    public static <T> JsonResult<T> fail(String errorCode, String errorMessage, T data) {
        JsonResult<T> result = getResult(errorCode, errorMessage, data);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    private static <T> JsonResult<T> getResult(String errorCode, String errorMessage, T data) {
        JsonResult<T> result = new JsonResult<>();
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        result.setData(data);
        return result;
    }
}
