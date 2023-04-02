package com.ruyuan2020.im.auth.security;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import com.ruyuan2020.im.common.security.token.properties.OAuth2ClientProperties;
import com.ruyuan2020.im.common.security.token.properties.Oauth2Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private Oauth2Properties oauth2Properties;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private DefaultTokenServices tokenServices;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 设置认证管理器
                .authenticationManager(authenticationManager)
                // 设置TokenStore
                .tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (CollectionUtil.isNotEmpty(oauth2Properties.getClients())) {
            for (OAuth2ClientProperties client : oauth2Properties.getClients()) {
                builder.withClient(client.getClientId())
                        .secret(client.getSecret())
                        .authorizedGrantTypes(StringUtils.split(client.getAuthorizedGrantTypes(), ","))
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                        .scopes(ArrayUtil.toArray(client.getScopes(), String.class))
                        .additionalInformation(SecurityConstants.CLIENT_KEY_REALM + ":" + client.getRealm());
            }
        }
    }
}
