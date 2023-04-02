package com.ruyuan2020.im.group.dao;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;
import com.ruyuan2020.im.group.domain.GroupMemberDO;
import com.ruyuan2020.im.group.domain.GroupMemberQuery;

import java.util.List;


/**
 * @author case
 */
public interface GroupMemberDAO extends BaseDAO<GroupMemberDO> {

    BasePage<GroupMemberDO> listByPage(GroupMemberQuery query);

    long count(Long groupId, Long memberId);

    List<Long> getByGroupId(Long groupId);

    BasePage<Long> listGroupMemberByPage(GroupMemberQuery query, Long memberId);

    long count(Long groupId);
}
