package com.ruyuan2020.im.group.service;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupMemberDTO;
import com.ruyuan2020.im.group.domain.GroupMemberQuery;

import java.util.List;

/**
 * @author case
 */
public interface GroupMemberService {

    BasePage<GroupMemberDTO> listByPage(GroupMemberQuery query);

    void join(GroupDTO groupDTO);

    List<Long> getByGroupId(Long groupId);

    BasePage<GroupMemberDTO> listGroupMember(GroupMemberQuery query);

}
