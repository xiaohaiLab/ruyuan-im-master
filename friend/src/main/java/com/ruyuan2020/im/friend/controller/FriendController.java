package com.ruyuan2020.im.friend.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.friend.domain.FriendRelationshipQuery;
import com.ruyuan2020.im.friend.domain.FriendRelationshipVO;
import com.ruyuan2020.im.friend.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/friend/account")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping
    public JsonResult<?> addFriend(Long requestId) {
        friendService.addFriend(requestId);
        return ResultHelper.ok();
    }

    @GetMapping
    public JsonResult<?> listByPage(FriendRelationshipQuery query) {
        return ResultHelper.ok(BeanCopierUtils.convert(friendService.listByPage(query), FriendRelationshipVO.class));
    }

}
