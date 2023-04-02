package com.ruyuan2020.im.account.dao;

import com.ruyuan2020.im.account.domain.UserAccountDO;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.account.domain.UserAccountQuery;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

/**
 * @author case
 */
public interface UserAccountDAO extends BaseDAO<UserAccountDO> {

    BasePage<UserAccountDO> listByPage(UserAccountQuery query);

    long countByUsername(String username);

    Optional<UserAccountDO> getByUsername(String username);

    long countByNickname(String nickname);

    long countByMobile(String mobile);

    List<UserAccountDO> getByIds(List<Long> ids);

    Optional<UserAccountDO> getByMobile(String mobile);
}
