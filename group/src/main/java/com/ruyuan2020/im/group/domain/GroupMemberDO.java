package com.ruyuan2020.im.group.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
@TableName("group_member")
public class GroupMemberDO extends BaseDO {

    /**
     * 群组id
     */
    private Long groupId;

    /**
     * 账户id
     */
    private Long memberId;
}