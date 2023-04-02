package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

/**
 * @author case
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final DefaultTokenServices tokenServices;

    @SneakyThrows
    @Override
    public AuthInfo authenticate(String token) {
        log.info("token:{}", token);
        OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(token);
        Oauth2AuthenticationToken oauth2AuthenticationToken = (Oauth2AuthenticationToken) oAuth2Authentication.getUserAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) oauth2AuthenticationToken.getPrincipal();
        return userDetails.getAuthInfo();
    }
}
