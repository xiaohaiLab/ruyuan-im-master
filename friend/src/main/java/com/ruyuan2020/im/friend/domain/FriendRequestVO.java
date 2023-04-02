package com.ruyuan2020.im.friend.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
public class FriendRequestVO extends BaseDomain {

    private Long id;

    private String username;

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