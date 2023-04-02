package com.ruyuan2020.im.chat.service;

import com.ruyuan2020.im.chat.domain.ChatDTO;
import com.ruyuan2020.im.chat.domain.ChatMemberDTO;

import java.util.List;

public interface ChatService {

    List<ChatMemberDTO> list();

    void save(ChatDTO chatDTO);
}
