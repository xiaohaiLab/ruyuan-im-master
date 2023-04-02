package com.ruyuan2020.im.security.authentication.wx;

import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.security.core.AuthenticationException;

/**
 * @author case
 */
public interface WxUserDetailsService {

    CustomUserDetails loadUser(WxAuthenticationToken wxAuthenticationToken) throws AuthenticationException;
}
