package com.ruyuan2020.im.auth.remote;

import com.ruyuan2020.im.admin.api.AdminApi;
import com.ruyuan2020.im.common.security.domain.AdminInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Component
public class AdminRemote implements Remote<AdminInfo> {

    @DubboReference(version = "1.0.0")
    private AdminApi adminApi;

    public AdminInfo login(String username, String password) {
        return adminApi.login(username, password);
    }

    @Override
    public AdminInfo loginByMobile(String mobile) {
        return null;
    }

    @Override
    public AdminInfo loginByUnionId(String unionId) {
        return null;
    }
}
