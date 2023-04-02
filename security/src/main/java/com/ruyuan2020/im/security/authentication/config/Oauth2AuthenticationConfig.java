package com.ruyuan2020.im.security.authentication.config;

import com.ruyuan2020.im.security.authentication.assembler.TokenAssemblerFactory;
import com.ruyuan2020.im.security.authentication.filter.Oauth2AuthenticationFilter;
import com.ruyuan2020.im.security.authentication.oauth2.Oauth2TokenParser;
import com.ruyuan2020.im.security.authentication.password.PasswordAuthenticationProvider;
import com.ruyuan2020.im.security.authentication.password.PasswordUserDetailsService;
import com.ruyuan2020.im.security.authentication.sms.MobileAuthenticationProvider;
import com.ruyuan2020.im.security.authentication.sms.MobileUserDetailsService;
import com.ruyuan2020.im.security.authentication.wx.WxAuthenticationProvider;
import com.ruyuan2020.im.security.authentication.wx.WxUserDetailsService;
import com.ruyuan2020.im.security.properties.SecurityProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author case
 */
@Component
public class Oauth2AuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private Oauth2TokenParser oauth2TokenParser;

    @Resource
    private TokenAssemblerFactory tokenAssemblerFactory;

    @Resource
    private PasswordUserDetailsService passwordUserDetailsService;

    @Resource
    private MobileUserDetailsService mobileUserDetailsService;

    @Resource
    private WxUserDetailsService wxUserDetailsService;

    @Resource
    private WxMpService wxService;

    @Override
    public void configure(HttpSecurity http) {
        Oauth2AuthenticationFilter oauth2AuthenticationFilter = new Oauth2AuthenticationFilter(securityProperties);
        oauth2AuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        oauth2AuthenticationFilter.setOauth2TokenParser(oauth2TokenParser);
        oauth2AuthenticationFilter.setTokenAssemblerFactory(tokenAssemblerFactory);
        oauth2AuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        oauth2AuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider();
        passwordAuthenticationProvider.setPasswordUserDetailsService(passwordUserDetailsService);

        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setMobileUserDetailsService(mobileUserDetailsService);

        WxAuthenticationProvider wxAuthenticationProvider = new WxAuthenticationProvider();
        wxAuthenticationProvider.setWxService(wxService);
        wxAuthenticationProvider.setWxUserDetailsService(wxUserDetailsService);

        http.authenticationProvider(passwordAuthenticationProvider)
                .authenticationProvider(mobileAuthenticationProvider)
                .authenticationProvider(wxAuthenticationProvider)
                .addFilterAfter(oauth2AuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
