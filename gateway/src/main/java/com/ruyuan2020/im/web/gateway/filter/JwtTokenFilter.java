package com.ruyuan2020.im.web.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import com.nimbusds.jose.JWSObject;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

/**
 * 令牌拦截器，把认证信息写入请求头，传递给资源服务
 *
 * @author case
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private final DefaultTokenServices tokenServices;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 判断认证类型是Bearer
        String token = request.getHeaders().getFirst(JwtTokenConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, JwtTokenConstants.JWT_PREFIX)) {
            return chain.filter(exchange);
        }
        // 解析令牌，获取payload
        token = StrUtil.replaceIgnoreCase(token, JwtTokenConstants.JWT_PREFIX, "");

        OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(token);
        Oauth2AuthenticationToken oauth2AuthenticationToken = (Oauth2AuthenticationToken) oAuth2Authentication.getUserAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) oauth2AuthenticationToken.getPrincipal();
        AuthInfo authInfo = userDetails.getAuthInfo();

        // 将payload写入请求头
        request = exchange.getRequest().mutate()
                .header(JwtTokenConstants.REALM_KEY, oauth2AuthenticationToken.getOauth2Request().getRealm())
                .header(JwtTokenConstants.AUTH_INFO_KEY, URLEncoder.encode(JSONUtil.toJsonStr(authInfo), "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
