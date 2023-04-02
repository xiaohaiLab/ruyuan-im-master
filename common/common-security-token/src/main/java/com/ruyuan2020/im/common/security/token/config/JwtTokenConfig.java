package com.ruyuan2020.im.common.security.token.config;

import com.google.common.collect.Maps;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author case
 */
@Configuration
@ConditionalOnProperty(prefix = "security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenConfig {

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Resource
    private TokenEnhancer jwtTokenEnhancer;

    @Resource
    private TokenStore tokenStore;

    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        // 添加自定义的JwtTokenEnhancer增强器
        enhancers.add(jwtTokenEnhancer);
        // 添加 JwtAccessTokenConverter，会把accessToken转成jwt
        enhancers.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(enhancers);
        services.setTokenEnhancer(tokenEnhancerChain);
        return services;
    }

    /**
     * 定义JwtTokenStore
     *
     * @return JwtTokenStore
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 自定义增强器
     *
     * @return JwtTokenEnhancer
     */
    @Bean
    @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    /**
     * 令牌增强器，可以添加额外信息，不会加密
     */
    public static class JwtTokenEnhancer implements TokenEnhancer {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            Map<String, Object> info = Maps.newHashMap();
            CustomUserDetails details = (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();
            info.put(JwtTokenConstants.ID_KEY, details.getAuthInfo().getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        }
    }
}
