package com.ruyuan2020.im.auth.remote;

import com.ruyuan2020.im.common.security.domain.TenantInfo;
import com.ruyuan2020.im.tenant.api.TenantApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Component
public class TenantRemote implements Remote<TenantInfo> {

    @DubboReference(version = "1.0.0")
    private TenantApi tenantApi;

    public TenantInfo login(String username, String password) {
        return tenantApi.login(username, password);
    }

    @Override
    public TenantInfo loginByMobile(String mobile) {
        return tenantApi.loginByMobile(mobile);
    }

    @Override
    public TenantInfo loginByUnionId(String unionId) {
        return null;
    }
}
