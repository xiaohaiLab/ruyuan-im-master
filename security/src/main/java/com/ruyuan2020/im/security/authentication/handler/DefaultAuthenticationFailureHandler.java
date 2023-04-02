package com.ruyuan2020.im.security.authentication.handler;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.security.exception.CustomAuthenticationException;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author case
 */
@Slf4j
@Component
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        String message = "认证失败";
        if (exception instanceof CustomAuthenticationException) {
            message = exception.getMessage();
        }
        String code = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        log.error(exception.getMessage(), exception);
        ServletUtils.renderJsonString(JSONUtil.toJsonStr(ResultHelper.fail(code, message)));
    }

}
