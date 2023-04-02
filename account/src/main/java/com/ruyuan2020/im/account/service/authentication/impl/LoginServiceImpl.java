package com.ruyuan2020.im.account.service.authentication.impl;

import com.ruyuan2020.im.account.dao.UserAccountDAO;
import com.ruyuan2020.im.account.domain.RegisterDTO;
import com.ruyuan2020.im.account.domain.UserAccountDO;
import com.ruyuan2020.im.account.service.UserAccountService;
import com.ruyuan2020.im.account.util.NicknameUtils;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.account.service.authentication.LoginService;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserAccountDAO userAccountDAO;

    private final UserAccountService userAccountService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AccountInfo login(String username, String password) {
        // 1. 查询指定用户名对应的站账户信息
        Optional<UserAccountDO> optional = userAccountDAO.getByUsername(username);
        // 2. 校验信息
        UserAccountDO userAccountDO = optional.orElseThrow(() -> new BaseSecurityException("登录用户不存在"));
        if (!passwordEncoder.matches(password, userAccountDO.getPassword())) {
            throw new BaseSecurityException("登录用户不存在");
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(userAccountDO.getId());
        accountInfo.setNickname(userAccountDO.getNickname());
        accountInfo.setStatus(userAccountDO.getStatus());
        return accountInfo;
    }

    @Override
    public AccountInfo loginByMobile(String mobile) {
        // 1. 查询指定用户名对应的站账户信息
        Optional<UserAccountDO> optional = userAccountDAO.getByMobile(mobile);
        UserAccountDO userAccountDO;
        if (!optional.isPresent()) {
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setMobile(mobile);
            registerDTO.setNickname(NicknameUtils.generateNickname());
            userAccountDO = userAccountService.register(registerDTO);
        } else {
            userAccountDO = optional.get();
        }
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(userAccountDO.getId());
        accountInfo.setNickname(userAccountDO.getNickname());
        accountInfo.setStatus(userAccountDO.getStatus());
        return accountInfo;
    }
}
