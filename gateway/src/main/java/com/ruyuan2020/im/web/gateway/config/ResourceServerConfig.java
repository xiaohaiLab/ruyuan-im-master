package com.ruyuan2020.im.web.gateway.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.ruyuan2020.im.web.gateway.properties.SecurityProperties;
import com.ruyuan2020.im.web.gateway.util.ResponseHelper;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.security.interfaces.RSAPublicKey;

/**
 * 资源服务器配置
 *
 * @author case
 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    /**
     * 授权管理器
     */
    @Resource
    private AuthorizationManager authorizationManager;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private RSAPublicKey rsaPublicKey;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // 配置公钥和jwt令牌转换器
        http.oauth2ResourceServer()
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()).publicKey(rsaPublicKey);
        // 配置认证和授权处理
        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
        // 设置白名单
        if (CollectionUtil.isNotEmpty(securityProperties.getWhiteListUrls())) {
            http.authorizeExchange()
                    .pathMatchers(ArrayUtil.toArray(securityProperties.getWhiteListUrls(), String.class)).permitAll();
        }
        http.authorizeExchange()
                // 设置授权管理器
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                // AccessDeniedException异常处理器
                .accessDeniedHandler(accessDeniedHandler())
                // AuthenticationException异常处理器
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().csrf().disable();
        return http.build();
    }

    /**
     * 授权失败处理器
     */
    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> ResponseHelper.fail(response, denied));
    }

    /**
     * 认证失败处理器
     */
    @Bean
    public ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (exchange, e) -> Mono.defer(() ->
                Mono.just(exchange.getResponse())).flatMap(response ->
                ResponseHelper.fail(response, e)
        );
    }

    /**
     * Jwt令牌转换器
     */
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(JwtTokenConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(JwtTokenConstants.AUTHORITY_KEY);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
