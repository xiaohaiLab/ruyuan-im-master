package com.ruyuan2020.im.chat.service.impl;

import com.ruyuan2020.im.chat.dao.ChatDAO;
import com.ruyuan2020.im.chat.dao.ChatMemberDAO;
import com.ruyuan2020.im.chat.domain.ChatDO;
import com.ruyuan2020.im.chat.domain.ChatDTO;
import com.ruyuan2020.im.chat.domain.ChatMemberDO;
import com.ruyuan2020.im.chat.domain.ChatMemberDTO;
import com.ruyuan2020.im.chat.service.ChatService;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private ChatMemberDAO chatMemberDAO;

    @Override
    public List<ChatMemberDTO> list() {
        Long memberId = SecurityUtils.getId();
        return chatMemberDAO.list(memberId).stream().map(it -> {
            ChatMemberDTO chatMemberDTO = it.clone(ChatMemberDTO.class);
            chatMemberDTO.setId(it.getChatId());
            return chatMemberDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(ChatDTO chatDTO) {
        Optional<ChatMemberDO> optional;
        if (Objects.equals(chatDTO.getType(), Constants.CHAT_TYPE_C2C)) {
            optional = chatMemberDAO.get(chatDTO.getType(), chatDTO.getMemberId(), chatDTO.getPeerId());
        } else {
            optional = chatMemberDAO.get(chatDTO.getType(), chatDTO.getPeerId());
        }
        Long chatId;
        if (optional.isPresent()) {
            chatId = optional.get().getChatId();
        } else {
            ChatDO chatDO = new ChatDO();
            chatDO.setType(chatDTO.getType());
            chatDAO.save(chatDO);
            chatId = chatDO.getId();
        }

        ChatMemberDO chatMemberDO = new ChatMemberDO();
        chatMemberDO.setType(chatDTO.getType());
        chatMemberDO.setChatId(chatId);
        chatMemberDO.setMemberId(chatDTO.getMemberId());
        chatMemberDO.setPeerId(chatDTO.getPeerId());
        chatMemberDO.setAvatar(chatDTO.getAvatar());
        chatMemberDO.setNickname(chatDTO.getNickname());
        chatMemberDAO.save(chatMemberDO);
    }
}
