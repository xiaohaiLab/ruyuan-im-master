package com.ruyuan2020.im.security.authentication.wx;

import com.ruyuan2020.im.common.security.token.oauth2.Oauth2AuthenticationToken;
import com.ruyuan2020.im.common.security.token.oauth2.detail.CustomUserDetails;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author case
 */
public class WxAuthenticationProvider implements AuthenticationProvider {

    private WxUserDetailsService wxUserDetailsService;

    private WxMpService wxService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxAuthenticationToken authenticationToken = (WxAuthenticationToken) authentication;
        String appId = authenticationToken.getAppId();
        // 切换appid
        if (!this.wxService.switchover(appId)) {
            throw new BaseSecurityException(MessageFormat.format("未找到对应appId={}的配置", appId));
        }
        String code = (String) authenticationToken.getPrincipal();
        try {
            // 通过code获取token
            WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            // 通过token获取微信用户信息
            WxOAuth2UserInfo user = wxService.getOAuth2Service().getUserInfo(accessToken, null);
            String unionId = user.getUnionId();
            authenticationToken.setPrincipal(unionId);
            CustomUserDetails customUserDetails = wxUserDetailsService.loadUser(authenticationToken);
            if (Objects.isNull(customUserDetails)) {
                throw new BaseSecurityException("用户信息不存在");
            }
            return new Oauth2AuthenticationToken(customUserDetails, authenticationToken.getOauth2Request());
        } catch (WxErrorException e) {
            throw new BaseSecurityException("获取微信用户信息失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WxAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setWxUserDetailsService(WxUserDetailsService wxUserDetailsService) {
        this.wxUserDetailsService = wxUserDetailsService;
    }

    public void setWxService(WxMpService wxService) {
        this.wxService = wxService;
    }
}
