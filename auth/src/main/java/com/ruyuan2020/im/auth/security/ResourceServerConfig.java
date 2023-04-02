package com.ruyuan2020.im.auth.security;

import cn.hutool.core.util.ArrayUtil;
import com.ruyuan2020.im.security.authentication.config.Oauth2AuthenticationConfig;
import com.ruyuan2020.im.security.authentication.handler.DefaultAccessDeniedHandler;
import com.ruyuan2020.im.security.authentication.handler.DefaultAuthenticationFailureHandler;
import com.ruyuan2020.im.security.code.config.CodeConfig;
import com.ruyuan2020.im.security.properties.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private Oauth2AuthenticationConfig oauth2AuthenticationConfig;

    @Resource
    private CodeConfig captchaSecurityConfig;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Resource
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .apply(oauth2AuthenticationConfig).and()
                .apply(captchaSecurityConfig).and()
                .authorizeRequests()
                .antMatchers(securityProperties.getAuthentication().getUrl())
                .permitAll()
                .antMatchers(ArrayUtil.toArray(securityProperties.getCode().getCaptcha().getUrls(), String.class))
                .permitAll()
                .antMatchers(ArrayUtil.toArray(securityProperties.getCode().getSms().getUrls(), String.class))
                .permitAll()
                .antMatchers(ArrayUtil.toArray(securityProperties.getWhiteListUrls(), String.class))
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    defaultAccessDeniedHandler.handle(request, response, accessDeniedException);
                })
                .authenticationEntryPoint((request, response, authenticationException) -> {
                    defaultAuthenticationFailureHandler.onAuthenticationFailure(request, response, authenticationException);
                });
    }
}
