package com.ruyuan2020.im.auth.remote;

import com.ruyuan2020.im.account.api.AccountApi;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Component
public class AccountRemote implements Remote<AccountInfo> {

    @DubboReference(version = "1.0.0")
    private AccountApi accountApi;

    public AccountInfo login(String username, String password) {
        return accountApi.login(username, password);
    }

    @Override
    public AccountInfo loginByMobile(String mobile) {
        return accountApi.loginByMobile(mobile);
    }

    @Override
    public AccountInfo loginByUnionId(String unionId) {
        return accountApi.loginByUnionId(unionId);
    }
}
