package com.ruyuan2020.im.gateway.util;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.protobuf.Result;

/**
 * @author case
 */
public class ResultConverter {

    public static <T> JsonResult<T> convert(Result result) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setSuccess(result.getSuccess());
        jsonResult.setErrorCode(result.getErrorCode());
        jsonResult.setErrorMessage(result.getErrorMessage());
        return jsonResult;
    }
}
