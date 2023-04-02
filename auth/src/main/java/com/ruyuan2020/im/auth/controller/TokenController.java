package com.ruyuan2020.im.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.ruyuan2020.im.auth.service.impl.TokenService;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/auth/info")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public JsonResult<?> authenticate(@RequestHeader(value = JwtTokenConstants.AUTHORIZATION_KEY) String authorization) {
        if (StrUtil.isNotBlank(authorization) && StrUtil.startWithIgnoreCase(authorization, JwtTokenConstants.JWT_PREFIX)) {
            String token = StrUtil.replaceIgnoreCase(authorization, JwtTokenConstants.JWT_PREFIX, "");
            AuthInfo authInfo = tokenService.authenticate(token);
            return ResultHelper.ok(authInfo);
        } else {
            throw new BaseSecurityException("认证信息不正确");
        }
    }
}
