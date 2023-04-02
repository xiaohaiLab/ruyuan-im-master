package com.ruyuan2020.im.friend.service;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.friend.domain.FriendRequestDTO;
import com.ruyuan2020.im.friend.domain.FriendRequestQuery;

/**
 * @author case
 */
public interface FriendRequestService {

    void submit(FriendRequestDTO friendRequestDTO);

    BasePage<FriendRequestDTO> list(FriendRequestQuery query);
}
