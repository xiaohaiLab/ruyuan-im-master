package com.ruyuan2020.im.chat.controller;

import com.ruyuan2020.im.chat.domain.ChatMemberVO;
import com.ruyuan2020.im.chat.service.ChatService;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/chat/account")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    JsonResult<?> list() {
        return ResultHelper.ok(BeanCopierUtils.convert(chatService.list(), ChatMemberVO.class));
    }
}
