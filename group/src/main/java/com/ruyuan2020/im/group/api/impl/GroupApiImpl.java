package com.ruyuan2020.im.group.api.impl;

import com.ruyuan2020.im.group.api.GroupApi;
import com.ruyuan2020.im.group.service.GroupMemberService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author case
 */
@AllArgsConstructor
@DubboService(version = "1.0.0", interfaceClass = GroupApi.class)
public class GroupApiImpl implements GroupApi {

    @Autowired
    private GroupMemberService groupMemberService;

    @Override
    public List<Long> getMemberIdByGroupId(Long groupId) {
        return groupMemberService.getByGroupId(groupId);
    }
}
