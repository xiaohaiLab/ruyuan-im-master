package com.ruyuan2020.im.friend.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
@TableName("friend_relationship")
public class FriendRelationshipDO extends BaseDO {

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 好友id
     */
    private Long friendId;

    private String nickname;
}