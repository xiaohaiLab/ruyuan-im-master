package com.ruyuan2020.im.web.gateway.config;

import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

/**
 * 授权管理器
 *
 * @author case
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 跨域时的预请求，放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        PathMatcher pathMatcher = new AntPathMatcher();
        // 拼接权限URL路径（GET:/api/demo）
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String authorityPath = method + ":" + path;

        return mono
                // 过滤权限信息
                .filter(Authentication::isAuthenticated) // 已经认证成功
                .flatMapIterable(Authentication::getAuthorities) // 获取权限
                .map(GrantedAuthority::getAuthority) // 遍历权限
                .any(authority -> { // 存在任何一个权限匹配拼接的URL路径
                    String authorityStr = authority.substring(JwtTokenConstants.AUTHORITY_PREFIX.length());
                    return pathMatcher.match(authorityStr, authorityPath);
                })
                // 返回AuthorizationDecision
                .map(AuthorizationDecision::new);
    }
}
