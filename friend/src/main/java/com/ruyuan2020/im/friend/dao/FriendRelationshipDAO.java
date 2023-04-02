package com.ruyuan2020.im.friend.dao;

import com.ruyuan2020.im.common.persistent.dao.BaseDAO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.friend.domain.FriendRelationshipDO;
import com.ruyuan2020.im.friend.domain.FriendRelationshipQuery;

/**
 * @author case
 */
public interface FriendRelationshipDAO extends BaseDAO<FriendRelationshipDO> {

    BasePage<FriendRelationshipDO> listByPage(FriendRelationshipQuery query);

    long count(Long accountId, Long friendId);
}
