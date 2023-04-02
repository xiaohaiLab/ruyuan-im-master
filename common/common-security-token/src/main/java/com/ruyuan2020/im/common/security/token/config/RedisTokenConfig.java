package com.ruyuan2020.im.common.security.token.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author case
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisTokenConfig {

    @Primary
    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2", name = "tokenStore", havingValue = "redis")
    public DefaultTokenServices tokenServices(TokenStore tokenStore) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        return services;
    }

    /**
     * 定义 RedisTokenStore
     *
     * @return RedisTokenStore
     */
    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2", name = "tokenStore", havingValue = "redis")
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
