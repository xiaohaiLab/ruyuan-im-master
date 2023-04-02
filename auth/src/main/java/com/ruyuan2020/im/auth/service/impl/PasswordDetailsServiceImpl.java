package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.auth.remote.Remote;
import com.ruyuan2020.im.auth.remote.RemoteFactory;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.security.authentication.password.PasswordAuthenticationToken;
import com.ruyuan2020.im.security.authentication.password.PasswordUserDetailsService;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author case
 */
@Service
public class PasswordDetailsServiceImpl implements PasswordUserDetailsService {

    @Resource
    private RemoteFactory<AuthInfo> remoteFactory;

    @Override
    public CustomUserDetails loadUser(PasswordAuthenticationToken passwordAuthenticationToken) throws AuthenticationException {
        String realm = passwordAuthenticationToken.getOauth2Request().getRealm();
        String username = (String) passwordAuthenticationToken.getPrincipal();
        String password = (String) passwordAuthenticationToken.getCredentials();
        Remote<AuthInfo> remote = remoteFactory.getRemote(realm);
        AuthInfo authInfo = remote.login(username, password);
        return new CustomUserDetails(authInfo);
    }
}
