package com.ruyuan2020.im.common.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseHelper {

    public static <T> void ok(HttpServletResponse response, T data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(JSON.toJSONString(ResultHelper.ok(data), SerializerFeature.DisableCircularReferenceDetect));
    }

    public static void fail(HttpServletResponse response, String errorCode, String errorMessage) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(JSON.toJSONString(ResultHelper.fail(errorCode, errorMessage)));
    }
}
