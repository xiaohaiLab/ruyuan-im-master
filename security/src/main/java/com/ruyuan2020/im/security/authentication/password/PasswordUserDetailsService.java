package com.ruyuan2020.im.security.authentication.password;

import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.security.core.AuthenticationException;

/**
 * @author case
 */
public interface PasswordUserDetailsService {

    CustomUserDetails loadUser(PasswordAuthenticationToken passwordAuthenticationToken) throws AuthenticationException;
}
