package com.ruyuan2020.im.security.authentication.password;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.security.authentication.assembler.TokenAssembler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author case
 */
@Component
public class PasswordTokenAssembler implements TokenAssembler {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    @Override
    public Oauth2AuthenticationToken assemble(HttpServletRequest request) {
        // 获取用户名
        String username = this.obtainUsername(request);
        if (username == null) {
            username = "";
        }
        // 获取密码
        String password = this.obtainPassword(request);
        if (password == null) {
            password = "";
        }
        return new PasswordAuthenticationToken(username, password);
    }

    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    }


}
