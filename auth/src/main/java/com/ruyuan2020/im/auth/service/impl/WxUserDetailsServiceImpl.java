package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.auth.remote.Remote;
import com.ruyuan2020.im.auth.remote.RemoteFactory;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.security.authentication.wx.WxAuthenticationToken;
import com.ruyuan2020.im.security.authentication.wx.WxUserDetailsService;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author case
 */
@Service
public class WxUserDetailsServiceImpl implements WxUserDetailsService {

    @Resource
    private RemoteFactory<AuthInfo> remoteFactory;

    @Override
    public CustomUserDetails loadUser(WxAuthenticationToken wxAuthenticationToken) throws AuthenticationException {
        String realm = wxAuthenticationToken.getOauth2Request().getRealm();
        String unionId = (String) wxAuthenticationToken.getPrincipal();
        Remote<AuthInfo> remote = remoteFactory.getRemote(realm);
        AuthInfo authInfo = remote.loginByUnionId(unionId);
        return new CustomUserDetails(authInfo);
    }
}
