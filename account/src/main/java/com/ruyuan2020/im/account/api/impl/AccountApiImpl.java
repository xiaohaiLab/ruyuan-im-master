package com.ruyuan2020.im.account.api.impl;

import com.ruyuan2020.im.account.api.AccountApi;
import com.ruyuan2020.im.account.dao.UserAccountDAO;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.account.service.UserAccountService;
import com.ruyuan2020.im.account.service.authentication.LoginService;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author case
 */
@DubboService(version = "1.0.0", interfaceClass = AccountApi.class)
@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {

    private final UserAccountDAO userAccountDAO;

    private final UserAccountService userAccountService;

    private final LoginService loginService;

    @Override
    public UserAccountDTO getByUsername(String username) {
        return userAccountDAO.getByUsername(username).orElseThrow(NotFoundException::new).clone(UserAccountDTO.class);
    }

    @Override
    public UserAccountDTO getById(Long id) {
        return userAccountDAO.getById(id).orElseThrow(NotFoundException::new).clone(UserAccountDTO.class);
    }

    @Override
    public List<UserAccountDTO> getByIds(List<Long> ids) {
        return userAccountService.getByIds(ids);
    }

    @Override
    public AccountInfo login(String username, String password) {
        return loginService.login(username, password);
    }

    @Override
    public AccountInfo loginByMobile(String mobile) {
        return loginService.loginByMobile(mobile);
    }

    @Override
    public AccountInfo loginByUnionId(String unionId) {
        return null;
    }
}
