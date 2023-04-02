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
@TableName("group_info")
public class GroupDO extends BaseDO {

    /**
     * 群主id
     */
    private Long ownerId;

    /**
     * 群名
     */
    private String name;
}