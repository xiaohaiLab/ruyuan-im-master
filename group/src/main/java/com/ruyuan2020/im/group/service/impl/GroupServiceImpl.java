package com.ruyuan2020.im.group.service.impl;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import com.ruyuan2020.im.group.domain.GroupDO;
import com.ruyuan2020.im.group.service.GroupMemberService;
import com.ruyuan2020.im.group.service.GroupService;
import com.ruyuan2020.im.group.dao.GroupDAO;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author case
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public BasePage<GroupDTO> listByPage(GroupQuery query) {
        return BeanCopierUtils.convert(groupDAO.listByPage(query), GroupDTO.class);
    }

    @Override
    @Transactional
    public void create(GroupDTO groupDTO) {
        Long accountId = SecurityUtils.getId();
        groupDTO.setOwnerId(accountId);
        Long groupId = groupDAO.save(groupDTO.clone(GroupDO.class));
        groupDTO.setId(groupId);
        groupMemberService.join(groupDTO);
    }
}
