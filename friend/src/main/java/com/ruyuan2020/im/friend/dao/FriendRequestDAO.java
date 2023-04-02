package com.ruyuan2020.im.friend.dao;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;
import com.ruyuan2020.im.friend.domain.FriendRequestDO;
import com.ruyuan2020.im.friend.domain.FriendRequestQuery;


/**
 * @author case
 */
public interface FriendRequestDAO extends BaseDAO<FriendRequestDO> {
    long count(Long accountId, Long friendId);

    BasePage<FriendRequestDO> list(FriendRequestQuery query);
}
