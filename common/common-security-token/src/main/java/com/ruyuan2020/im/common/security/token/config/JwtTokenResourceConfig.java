package com.ruyuan2020.im.common.security.token.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import com.ruyuan2020.im.common.security.token.converter.CustomUserAuthenticationConverter;
import com.ruyuan2020.im.common.security.token.properties.Oauth2Properties;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * TokenStore和Enhancer配置
 *
 * @author case
 */
@Configuration
@ConditionalOnProperty(prefix = "security.oauth2", name = "publicKey")
public class JwtTokenResourceConfig {

    @Resource
    private Oauth2Properties oauth2Properties;

    /**
     * 定义JwtAccessTokenConverter增强器
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(RSAPublicKey rsaPublicKey) {
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        // 替换JwtAccessTokenConverter中默认的DefaultUserAuthenticationConverter
        // 为自定义的CustomUserAuthenticationConverter
        defaultAccessTokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置公钥
        jwtAccessTokenConverter.setVerifier(new RsaVerifier(rsaPublicKey));
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
        return jwtAccessTokenConverter;
    }

    /**
     * 定义密钥对
     *
     * @return 密钥对
     */
    @SneakyThrows
    @Bean
    public RSAPublicKey rsaPublicKey() {
        ClassPathResource classPathResource = new ClassPathResource(oauth2Properties.getPublicKey());
        InputStream is = classPathResource.getInputStream();
        String publicKeyData = IoUtil.read(is).toString();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
