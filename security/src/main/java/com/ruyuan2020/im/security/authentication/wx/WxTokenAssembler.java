package com.ruyuan2020.im.security.authentication.wx;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.security.authentication.assembler.TokenAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author case
 */
@Component
public class WxTokenAssembler implements TokenAssembler {

    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";
    public static final String SPRING_SECURITY_FORM_APPID_KEY = "appId";

    @Override
    public Oauth2AuthenticationToken assemble(HttpServletRequest request) {
        // 获取用户名
        String code = this.obtainCode(request);
        if (code == null) {
            code = "";
        }
        // 获取密码
        String appId = this.obtainAppId(request);
        if (appId == null) {
            appId = "";
        }
        WxAuthenticationToken wxAuthenticationToken = new WxAuthenticationToken(code);
        wxAuthenticationToken.setAppId(appId);
        return wxAuthenticationToken;
    }

    private String obtainCode(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_CODE_KEY);
    }

    private String obtainAppId(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_APPID_KEY);
    }


}
