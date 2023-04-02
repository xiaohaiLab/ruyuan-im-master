package com.ruyuan2020.im.auth.api.impl;

import com.ruyuan2020.im.account.api.AuthApi;
import com.ruyuan2020.im.auth.service.impl.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;

/**
 * @author case
 */
@Slf4j
@DubboService(version = "1.0.0", interfaceClass = AuthApi.class)
@RequiredArgsConstructor
public class AuthApiImpl implements AuthApi {

    private final TokenService tokenService;

    @SneakyThrows
    @Override
    public boolean authenticate(String token) {
        try {
            tokenService.authenticate(token);
            return true;
        } catch (InvalidSignatureException e) {
            return false;
        }
    }
}
