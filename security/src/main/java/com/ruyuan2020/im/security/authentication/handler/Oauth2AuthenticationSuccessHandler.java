package com.ruyuan2020.im.security.authentication.handler;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2Request;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Oauth2认证成功处理
 *
 * @author case
 */
@Component
public class Oauth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private ClientDetailsService clientDetailsService;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Oauth2AuthenticationToken oauth2AuthenticationToken = (Oauth2AuthenticationToken) authentication;
        Oauth2Request oauth2Request = oauth2AuthenticationToken.getOauth2Request();

        // 通过clientId读取客户端详细信息
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(oauth2Request.getClientId());

        // OAuth2Request用来查找请求的token是否存在，JwtStore不支持
        TokenRequest tokenRequest = new TokenRequest(
                MapUtil.empty(), clientDetails.getClientId(), clientDetails.getScope(), oauth2Request.getGrantType()
        );
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        // 创建Oauth OAuth2AccessToken
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, oauth2AuthenticationToken);
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        // 令牌以json方式返回给前端
        ServletUtils.renderJsonString(JSONUtil.toJsonStr(ResultHelper.ok(token)));
    }
}
