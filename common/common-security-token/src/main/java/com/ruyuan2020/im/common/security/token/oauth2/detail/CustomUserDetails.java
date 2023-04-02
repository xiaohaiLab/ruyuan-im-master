package com.ruyuan2020.im.common.security.token.oauth2.detail;

import cn.hutool.core.collection.CollUtil;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author case
 */
public class CustomUserDetails implements UserDetails, CredentialsContainer {

    protected AuthInfo authInfo;

    protected Collection<GrantedAuthority> authorities;

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public CustomUserDetails(AuthInfo authInfo) {
        if (CollUtil.isNotEmpty(authInfo.getPriorities())) {
            this.authorities = authInfo.getPriorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } else {
            this.authorities = AuthorityUtils.NO_AUTHORITIES;
        }

        this.authInfo = authInfo;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
