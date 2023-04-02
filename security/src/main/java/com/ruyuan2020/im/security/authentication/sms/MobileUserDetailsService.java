package com.ruyuan2020.im.security.authentication.sms;

import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.security.core.AuthenticationException;

/**
 * @author case
 */
public interface MobileUserDetailsService {

    CustomUserDetails loadUser(MobileAuthenticationToken mobileAuthenticationToken) throws AuthenticationException;
}
