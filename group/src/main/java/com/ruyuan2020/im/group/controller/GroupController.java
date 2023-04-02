package com.ruyuan2020.im.group.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupQuery;
import com.ruyuan2020.im.group.domain.GroupVO;
import com.ruyuan2020.im.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/group/account")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public JsonResult<?> create(@RequestBody GroupVO groupVO) {
        groupService.create(groupVO.clone(GroupDTO.class));
        return ResultHelper.ok();
    }

    @GetMapping
    public JsonResult<?> listByPage(GroupQuery query) {
        return ResultHelper.ok(BeanCopierUtils.convert(groupService.listByPage(query), GroupVO.class));
    }

}
