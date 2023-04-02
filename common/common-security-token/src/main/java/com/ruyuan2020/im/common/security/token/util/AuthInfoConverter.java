package com.ruyuan2020.im.common.security.token.util;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.security.domain.AdminInfo;
import com.ruyuan2020.im.common.security.domain.AuthInfo;
import com.ruyuan2020.im.common.security.domain.TenantInfo;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;

/**
 * @author case
 */
public class AuthInfoConverter {

    public static AuthInfo convert(String realm, String authInfoJsonStr) {
        if ("account".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, AccountInfo.class);
        } else if ("admin".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, AdminInfo.class);
        } else if ("tenant".equals(realm)) {
            return JSONUtil.toBean(authInfoJsonStr, TenantInfo.class);
        }
        throw new BaseSecurityException("不支持的认证域");
    }
}
