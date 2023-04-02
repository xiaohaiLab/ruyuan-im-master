package com.ruyuan2020.im.security.authentication.wx;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;

/**
 * @author case
 */
public class WxAuthenticationToken extends Oauth2AuthenticationToken {

    private String appId;

    public WxAuthenticationToken(Object principal) {
        super(principal);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
