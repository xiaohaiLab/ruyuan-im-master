package com.ruyuan2020.im.friend.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
public class FriendRelationshipVO extends BaseDomain {

    private Long id;

    private Long accountId;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 好友昵称
     */
    private String nickname;
}