package com.ruyuan2020.im.account.service.authentication;

import com.ruyuan2020.im.common.security.domain.AccountInfo;

public interface LoginService {

    AccountInfo login(String username, String password);

    AccountInfo loginByMobile(String mobile);
}
