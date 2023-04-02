package com.ruyuan2020.im.group.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupMemberQuery;
import com.ruyuan2020.im.group.domain.GroupMemberVO;
import com.ruyuan2020.im.group.domain.GroupVO;
import com.ruyuan2020.im.group.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/group/account/member")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @GetMapping("/page")
    public JsonResult<?> listByPage(GroupMemberQuery query) {
        return ResultHelper.ok(BeanCopierUtils.convert(groupMemberService.listByPage(query), GroupMemberVO.class));
    }

    @GetMapping("/groupMember")
    public JsonResult<?> listGroupMember(GroupMemberQuery query) {
        return ResultHelper.ok(BeanCopierUtils.convert(groupMemberService.listGroupMember(query), GroupMemberVO.class));
    }

    @PostMapping
    public JsonResult<?> join(@RequestBody GroupVO groupVO) {
        groupMemberService.join(groupVO.clone(GroupDTO.class));
        return ResultHelper.ok();
    }
}
