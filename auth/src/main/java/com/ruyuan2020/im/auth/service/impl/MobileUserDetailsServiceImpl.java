package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.auth.remote.Remote;
import com.ruyuan2020.im.auth.remote.RemoteFactory;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.security.authentication.sms.MobileUserDetailsService;
import com.ruyuan2020.im.security.authentication.sms.MobileAuthenticationToken;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author case
 */
@Service
public class MobileUserDetailsServiceImpl implements MobileUserDetailsService {

    @Resource
    private RemoteFactory<AuthInfo> remoteFactory;

    @Override
    public CustomUserDetails loadUser(MobileAuthenticationToken mobileAuthenticationToken) throws AuthenticationException {
        String realm = mobileAuthenticationToken.getOauth2Request().getRealm();
        String Mobile = (String) mobileAuthenticationToken.getPrincipal();
        Remote<AuthInfo> remote = remoteFactory.getRemote(realm);
        AuthInfo authInfo = remote.loginByMobile(Mobile);
        return new CustomUserDetails(authInfo);
    }
}
