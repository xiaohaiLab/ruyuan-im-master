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
@TableName("friend_request")
public class FriendRequestDO extends BaseDO {

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 好友id
     */
    private Long friendId;

    /**
     * 好友昵称
     */
    private String nickname;

    /**
     * 申请内容
     */
    private String content;

    /**
     * 申请状态
     */
    private Integer status;
}