package com.ruyuan2020.im.common.security.token.oauth2;

import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author case
 */
public class Oauth2AuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;

    private Oauth2Request oauth2Request;

    public Oauth2AuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Oauth2AuthenticationToken(CustomUserDetails principal, Oauth2Request oauth2Request) {
        super(principal.getAuthorities());
        this.principal = principal;
        this.oauth2Request = oauth2Request;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    public Oauth2Request getOauth2Request() {
        return oauth2Request;
    }

    public void setOauth2Request(Oauth2Request oauth2Request) {
        this.oauth2Request = oauth2Request;
    }
}
