package com.ruyuan2020.im.security.authentication.handler;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author case
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        ServletUtils.renderJsonString(JSONUtil.toJsonStr(ResultHelper.fail(HttpStatus.FORBIDDEN.toString(), exception.getMessage())));
    }
}
