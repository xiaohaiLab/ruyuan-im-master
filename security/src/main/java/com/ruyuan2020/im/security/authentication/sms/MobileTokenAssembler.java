package com.ruyuan2020.im.security.authentication.sms;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.security.authentication.assembler.TokenAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author case
 */
@Component
public class MobileTokenAssembler implements TokenAssembler {

    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    @Override
    public Oauth2AuthenticationToken assemble(HttpServletRequest request) {
        // 获取手机号
        String mobile = this.obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }
        return new MobileAuthenticationToken(mobile);
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_MOBILE_KEY);
    }
}
