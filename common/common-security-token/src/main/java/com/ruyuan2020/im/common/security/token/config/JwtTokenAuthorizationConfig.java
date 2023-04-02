package com.ruyuan2020.im.common.security.token.config;

import com.ruyuan2020.im.common.security.token.converter.CustomUserAuthenticationConverter;
import com.ruyuan2020.im.common.security.token.properties.Oauth2Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * TokenStore和Enhancer配置
 *
 * @author case
 */
@Configuration
@ConditionalOnProperty(prefix = "security.oauth2", name = "keyPair")
public class JwtTokenAuthorizationConfig {

    @Resource
    private Oauth2Properties oauth2Properties;

    /**
     * 定义JwtAccessTokenConverter增强器
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(KeyPair keyPair) {
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        // 替换JwtAccessTokenConverter中默认的DefaultUserAuthenticationConverter
        // 为自定义的CustomUserAuthenticationConverter
        defaultAccessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置公钥
        jwtAccessTokenConverter.setKeyPair(keyPair);
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        return jwtAccessTokenConverter;
    }

    /**
     * 定义密钥对
     *
     * @return 密钥对
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource(oauth2Properties.getKeyPair()),
                oauth2Properties.getStorepass().toCharArray()
        );
        return factory.getKeyPair(
                oauth2Properties.getAlias(),
                oauth2Properties.getKeypass().toCharArray()
        );
    }
}
