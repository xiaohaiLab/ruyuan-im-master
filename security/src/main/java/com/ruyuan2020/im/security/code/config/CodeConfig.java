package com.ruyuan2020.im.security.code.config;

import com.ruyuan2020.im.security.code.filter.VerifyCodeFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CodeConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Resource
    private VerifyCodeFilter verifyCodeFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(verifyCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
