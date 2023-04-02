package com.ruyuan2020.im.common.web.util;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.security.constant.JwtTokenConstants;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.security.domain.AdminInfo;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.domain.TenantInfo;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SecurityUtils {

    /**
     * 获取用户账户
     **/
    public static Long getId() {
        return getAuthInfo().getId();
    }

    /**
     * 获取用户
     **/
    @SneakyThrows
    public static AuthInfo getAuthInfo() {
        HttpServletRequest request = ServletUtils.getRequest();
        String realm = request.getHeader(JwtTokenConstants.REALM_KEY);
        String authInfoStr = request.getHeader(JwtTokenConstants.AUTH_INFO_KEY);
        if (Objects.isNull(realm) || Objects.isNull(authInfoStr)) {
            throw new BusinessException("请传入认证头");
        }
        String authInfoJsonStr = URLDecoder.decode(authInfoStr, StandardCharsets.UTF_8.name());
        return getAuthInfo(realm, authInfoJsonStr);
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static AuthInfo getAuthInfo(String realm, String authInfoJsonStr) {
        if ("account".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, AccountInfo.class);
        } else if ("admin".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, AdminInfo.class);
        } else if ("tenant".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, TenantInfo.class);
        }
        throw new BusinessException("不支持的认证域");
    }
}
