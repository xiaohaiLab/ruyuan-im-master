package com.ruyuan2020.im.security.authentication.oauth2;

import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Oauth2令牌解析
 *
 * @author case
 */
@Component
public class Oauth2TokenParser {

    public static final String OAUTH2_GRANT_TYPE_KEY = "grantType";

    @Resource
    private ClientDetailsService clientDetailsService;

    public Oauth2Request parser(HttpServletRequest request) {
        // 从请求头中获取认证令牌
        String basic = request.getHeader("Authorization");

        // 必须是basic认证方式
        if (basic == null || !basic.startsWith("Basic ")) {
            throw new BaseSecurityException("请求头中缺少认证信息");
        }

        // 解密basic认证信息，获取clientId和secret
        String[] tokens = extractAndDecodeHeader(basic);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String secret = tokens[1];
        // 读取客户端信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new BaseSecurityException("ClientId配置信息不存在");
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), secret)) {
            throw new BaseSecurityException("Secret信息不匹配");
        }
        // 请求参数中获取grantType
        String grantType = obtainGrantType(request);
        // 判断客户端信息中是否支持当前传递的grantType
        boolean exist = clientDetails.getAuthorizedGrantTypes().stream()
                .anyMatch(authorizedGrantType -> Objects.equals(authorizedGrantType, grantType));
        if (!exist) {
            throw new BaseSecurityException("GrantType信息不匹配");
        }
        // 获取认证域
        String realm = (String) clientDetails.getAdditionalInformation().get(SecurityConstants.CLIENT_KEY_REALM);
        // 返回Oauth2请求信息
        return new Oauth2Request(clientId, grantType, clientDetails.getScope(), realm);
    }

    private String[] extractAndDecodeHeader(String basic) {

        byte[] base64Token = basic.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    private String obtainGrantType(HttpServletRequest request) {
        return request.getParameter(OAUTH2_GRANT_TYPE_KEY);
    }
}
