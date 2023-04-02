package com.ruyuan2020.im.c2c.controller;

import com.ruyuan2020.im.c2c.domain.FetchQuery;
import com.ruyuan2020.im.c2c.service.MessageSendService;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/c2c/fetch")
public class MessageFetchController {

    @Autowired
    private MessageSendService messageSendService;

    @GetMapping
    public JsonResult<?> fetch(FetchQuery fetchQuery) {
        return ResultHelper.ok(messageSendService.fetch(
                fetchQuery.getChatId(),
                fetchQuery.getStartMessageId(),
                fetchQuery.getStopMessageId(),
                fetchQuery.getSize()));
    }
}
