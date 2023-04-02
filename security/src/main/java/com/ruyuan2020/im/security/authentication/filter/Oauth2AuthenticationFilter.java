package com.ruyuan2020.im.security.authentication.filter;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2Request;
import com.ruyuan2020.im.security.authentication.assembler.TokenAssemblerFactory;
import com.ruyuan2020.im.security.authentication.oauth2.Oauth2TokenParser;
import com.ruyuan2020.im.security.properties.SecurityProperties;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户名密码认证拦截器
 *
 * @author case
 */
public class Oauth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;
    private Oauth2TokenParser oauth2TokenParser;
    private TokenAssemblerFactory tokenAssemblerFactory;

    public Oauth2AuthenticationFilter(SecurityProperties securityProperties) {
        // 拦截配置的URL
        super(new AntPathRequestMatcher(securityProperties.getAuthentication().getUrl(), null));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            // 解析Basic令牌的请求头
            Oauth2Request oauth2Request = oauth2TokenParser.parser(request);

            // 组装认证令牌
            Oauth2AuthenticationToken authRequest = tokenAssemblerFactory.getAssembler(oauth2Request.getGrantType()).assemble(request);
            // 生成认证令牌
            authRequest.setOauth2Request(oauth2Request);
            this.setDetails(request, authRequest);
            // 交给AuthenticationManager去进行认证
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, Oauth2AuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public void setOauth2TokenParser(Oauth2TokenParser oauth2TokenParser) {
        this.oauth2TokenParser = oauth2TokenParser;
    }

    public void setTokenAssemblerFactory(TokenAssemblerFactory tokenAssemblerFactory) {
        this.tokenAssemblerFactory = tokenAssemblerFactory;
    }
}
