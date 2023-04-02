package com.ruyuan2020.im.account.api;

import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.exception.NotFoundException;
import com.ruyuan2020.im.common.security.domain.AccountInfo;

import java.util.List;

public interface AccountApi {

    UserAccountDTO getByUsername(String username) throws NotFoundException, BusinessException;

    UserAccountDTO getById(Long id) throws NotFoundException, BusinessException;

    List<UserAccountDTO> getByIds(List<Long> ids) throws NotFoundException, BusinessException;

    AccountInfo login(String username, String password) throws NotFoundException, BusinessException;

    AccountInfo loginByMobile(String mobile) throws NotFoundException, BusinessException;

    AccountInfo loginByUnionId(String unionId) throws NotFoundException, BusinessException;
}
