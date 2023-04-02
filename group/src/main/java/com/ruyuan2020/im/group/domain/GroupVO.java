package com.ruyuan2020.im.group.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;


/**
 * @author case
 */
@Getter
@Setter
public class GroupVO extends BaseDomain {

    private Long id;

    /**
     * 群主id
     */
    private Long ownerId;

    /**
     * 群名
     */
    private String name;
}