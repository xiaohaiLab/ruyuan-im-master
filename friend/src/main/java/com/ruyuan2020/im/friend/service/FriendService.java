package com.ruyuan2020.im.friend.service;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.friend.domain.FriendRelationshipDTO;
import com.ruyuan2020.im.friend.domain.FriendRelationshipQuery;

/**
 * @author case
 */
public interface FriendService {
    void addFriend(Long requestId);

    BasePage<FriendRelationshipDTO> listByPage(FriendRelationshipQuery query);
}
