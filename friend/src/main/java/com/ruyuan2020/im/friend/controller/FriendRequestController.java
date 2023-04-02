package com.ruyuan2020.im.friend.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.friend.domain.FriendRequestDTO;
import com.ruyuan2020.im.friend.domain.FriendRequestQuery;
import com.ruyuan2020.im.friend.domain.FriendRequestVO;
import com.ruyuan2020.im.friend.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/friend/account/request")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping
    public JsonResult<?> submit(@RequestBody FriendRequestVO friendRequestVO) {
        friendRequestService.submit(friendRequestVO.clone(FriendRequestDTO.class));
        return ResultHelper.ok();
    }

    @GetMapping
    public JsonResult<?> listByPage(FriendRequestQuery query) {
        return ResultHelper.ok(BeanCopierUtils.convert(friendRequestService.list(query), FriendRequestVO.class));
    }
}
