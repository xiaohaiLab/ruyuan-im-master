package com.ruyuan2020.im.friend.domain;

import com.ruyuan2020.im.common.persistent.domain.BaseQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class FriendRelationshipQuery extends BaseQuery {

    private Long accountId;
}
