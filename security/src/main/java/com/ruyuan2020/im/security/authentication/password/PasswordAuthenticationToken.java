package com.ruyuan2020.im.security.authentication.password;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;

/**
 * @author case
 */
public class PasswordAuthenticationToken extends Oauth2AuthenticationToken {

    private Object credentials;

    public PasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal);
        this.credentials = credentials;
    }

    public PasswordAuthenticationToken(Object principal) {
        super(principal);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
