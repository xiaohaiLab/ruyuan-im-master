package com.ruyuan2020.im.auth.security;

import com.ruyuan2020.im.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * @author case
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityProperties securityProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
