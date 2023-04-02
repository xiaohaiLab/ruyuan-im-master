package com.ruyuan2020.im.security.authentication.sms;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;

/**
 * @author case
 */
public class MobileAuthenticationToken extends Oauth2AuthenticationToken {

    public MobileAuthenticationToken(Object principal) {
        super(principal);
    }
}
