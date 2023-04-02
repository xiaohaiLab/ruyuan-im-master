package com.ruyuan2020.im.group.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
public class GroupMemberDTO extends BaseDomain {

    private Long id;

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 账户id
     */
    private Long memberId;

    private String nickname;

    private String avatar;
}