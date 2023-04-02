package com.ruyuan2020.im.security.authentication.sms;

import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

/**
 * @author case
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private MobileUserDetailsService mobileUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        CustomUserDetails customUserDetails = mobileUserDetailsService.loadUser(authenticationToken);
        if (Objects.isNull(customUserDetails)) {
            throw new BaseSecurityException("用户信息不存在");
        }
        return new Oauth2AuthenticationToken(customUserDetails, authenticationToken.getOauth2Request());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setMobileUserDetailsService(MobileUserDetailsService mobileUserDetailsService) {
        this.mobileUserDetailsService = mobileUserDetailsService;
    }
}
