package com.ruyuan2020.im.account.service;

import com.ruyuan2020.im.account.domain.UserAccountDO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.account.domain.RegisterDTO;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.account.domain.UserAccountQuery;

import java.util.List;

public interface UserAccountService {

    BasePage<UserAccountDTO> listByPage(UserAccountQuery query);

    UserAccountDTO getById(Long id);

    void update(UserAccountDTO userAccountDTO);

    void modifyPassword(Long id, String password);

    UserAccountDO register(RegisterDTO registerDTO);

    void modifyNickname(Long id, String nickname);

    List<UserAccountDTO> getByIds(List<Long> ids);
}
