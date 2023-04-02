package com.ruyuan2020.im.chat.api.impl;

import com.ruyuan2020.im.chat.api.ChatApi;
import com.ruyuan2020.im.chat.dao.ChatDAO;
import com.ruyuan2020.im.chat.domain.ChatDO;
import com.ruyuan2020.im.chat.domain.ChatDTO;
import com.ruyuan2020.im.chat.service.ChatService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author case
 */
@DubboService(version = "1.0.0", interfaceClass = ChatApi.class)
public class ChatApiImpl implements ChatApi {

    @Autowired
    private ChatService chatService;

    @Override
    public void addChat(ChatDTO chatDTO) {
        chatService.save(chatDTO);
    }
}
