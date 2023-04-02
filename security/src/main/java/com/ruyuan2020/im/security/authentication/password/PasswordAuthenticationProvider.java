package com.ruyuan2020.im.security.authentication.password;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

/**
 * 用户名密码授权Provider实现
 *
 * @author case
 */
public class PasswordAuthenticationProvider implements AuthenticationProvider {

    private PasswordUserDetailsService passwordUserDetailsService;

    /**
     * 认证
     *
     * @param authentication 认证令牌
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordAuthenticationToken authenticationToken = (PasswordAuthenticationToken) authentication;
        // 通过令牌去获取认证后的用户信息
        CustomUserDetails customUserDetails = passwordUserDetailsService.loadUser(authenticationToken);
        if (Objects.isNull(customUserDetails)) {
            throw new BaseSecurityException("用户信息不存在");
        }
        // 包装成实现了Authentication的Oauth2AuthenticationToken返回
        return new Oauth2AuthenticationToken(customUserDetails, authenticationToken.getOauth2Request());
    }

    /**
     * 是否支持 PasswordAuthenticationToken
     *
     * @param authentication 认证令牌
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setPasswordUserDetailsService(PasswordUserDetailsService passwordUserDetailsService) {
        this.passwordUserDetailsService = passwordUserDetailsService;
    }
}
