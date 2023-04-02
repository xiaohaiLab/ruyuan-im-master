package com.ruyuan2020.im.common.security.token.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.Oauth2Request;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.common.security.token.util.AuthInfoConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;
import java.util.Set;

/**
 * 自定义用户认证增强器
 *
 * @author case
 */
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Oauth2AuthenticationToken token = (Oauth2AuthenticationToken) authentication;
        CustomUserDetails userDetails = (CustomUserDetails) token.getPrincipal();
        Oauth2Request oauth2Request = token.getOauth2Request();
        AuthInfo authInfo = userDetails.getAuthInfo();
        authInfo.setPriorities(null);
        Map<String, Object> response = MapUtil.newHashMap();
        // 设置认证的用户信息
        response.put(JwtTokenConstants.REALM_KEY, oauth2Request.getRealm());
        response.put(JwtTokenConstants.AUTH_INFO_KEY, JSONUtil.toJsonStr(authInfo));
        // 设置授权信息
        Set<String> authorities = CollUtil.newHashSet(oauth2Request.getScope());
        authorities.addAll(AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        if (CollUtil.isNotEmpty(authorities)) {
            response.put(AUTHORITIES, authorities);
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        String realm = (String) map.get(JwtTokenConstants.REALM_KEY);
        String authInfoStr = (String) map.get(JwtTokenConstants.AUTH_INFO_KEY);
        AuthInfo authInfo = AuthInfoConverter.convert(realm, authInfoStr);
        CustomUserDetails userDetails = new CustomUserDetails(authInfo);
        Oauth2AuthenticationToken oauth2AuthenticationToken = new Oauth2AuthenticationToken(userDetails);
        oauth2AuthenticationToken.setOauth2Request(new Oauth2Request(null, null, null, realm));
        return oauth2AuthenticationToken;
    }
}
