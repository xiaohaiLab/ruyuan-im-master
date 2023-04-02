package com.ruyuan2020.im.group.service;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupQuery;

/**
 * @author case
 */
public interface GroupService {

    BasePage<GroupDTO> listByPage(GroupQuery query);

    void create(GroupDTO groupDTO);
}
