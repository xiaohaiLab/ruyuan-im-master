package com.ruyuan2020.im.c2c.service;

import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.im.domain.MessageJsonPush;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;

import java.util.List;

public interface MessageSendService {

    void save(List<MessageSendJsonRequest> requests, Long chatId);

    void updateAck(Long chatId, Long memberId, Integer clientId, Long messageId);

    BasePage<MessageJsonPush> fetch(Long chatId, Long startMessageId, Long stopMessageId, int size);
}
