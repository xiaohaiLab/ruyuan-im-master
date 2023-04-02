package com.ruyuan2020.im.c2g.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan2020.im.c2g.dao.C2gMessageAckDAO;
import com.ruyuan2020.im.c2g.domain.C2gMessageAckDO;
import com.ruyuan2020.im.c2g.service.MessageSendService;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.MessageJsonFetch;
import com.ruyuan2020.im.common.im.domain.MessageJsonPush;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author case
 */
@Slf4j
public abstract class AbstractMessageSendService implements MessageSendService {

    private static final String ACK_KEY_PREFIX = "IM::C2G_MESSAGE_ACK";

    private static final String ACK_COUNT_KEY_PREFIX = "IM::C2G_MESSAGE_ACK_COUNT";

    private static final Integer ACK_THRESHOLD = 100;

    @Value("#{'topic-message-send-response-'.concat('${spring.profiles.active}')}")
    private String TOPIC_MESSAGE_SEND_RESPONSE;

    @Value("#{'topic-message-push-'.concat('${spring.profiles.active}')}")
    private String TOPIC_MESSAGE_PUSH;

    @Value("#{'topic-message-fetch-'.concat('${spring.profiles.active}')}")
    private String TOPIC_MESSAGE_FETCH;

    @Autowired
    protected RedisTemplate<String, Long> redisTemplate;

    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    protected C2gMessageAckDAO c2gMessageAckDAO;

    @Override
    @Transactional
    public void updateAck(Long chatId, Long memberId, Integer clientId, Long messageId) {
        String businessKey = chatId + "::" + memberId + "::" + clientId;
        // 组装redis key
        String key = ACK_KEY_PREFIX + "::" + businessKey;
        // 获取缓存在redis的ackId
        Optional<Long> ackOptional = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        Long storeAckId = ackOptional.orElse(0L);
        // 如果messageId大于等于缓存的ackId，就更新缓存
        if (messageId.compareTo(storeAckId) > 0) {
            redisTemplate.opsForValue().set(key, messageId);
            // 记录ack次数
            Optional<Long> countOptional = Optional.ofNullable(redisTemplate.boundValueOps(ACK_COUNT_KEY_PREFIX + "::" + businessKey).increment());
            // 如果ack的次数是 ACK_THRESHOLD 的整数倍，更新数据库
            Long ackCount = countOptional.orElse(1L);
            if (ackCount % ACK_THRESHOLD == 0) {
                long count = c2gMessageAckDAO.count(chatId, memberId, clientId);
                if (count == 0) {
                    C2gMessageAckDO c2gMessageAckDO = new C2gMessageAckDO();
                    c2gMessageAckDO.setChatId(chatId);
                    c2gMessageAckDO.setMemberId(memberId);
                    c2gMessageAckDO.setClientId(clientId);
                    c2gMessageAckDO.setLastAckMessageId(storeAckId);
                    c2gMessageAckDAO.save(c2gMessageAckDO);
                } else {
                    c2gMessageAckDAO.updateAck(chatId, memberId, clientId, storeAckId);
                }
            }
        }
    }

    protected void sendMessageSendResponse(MessageSendJsonRequest request) {
        MessageSendJsonResponse message = new MessageSendJsonResponse();
        message.setChatId(request.getChatId());
        message.setMessageId(request.getMessageId());
        message.setChatType(request.getChatType());
        message.setFromId(request.getFromId());
        message.setToId(request.getToId());
        message.setSequence(request.getSequence());
        message.setTimestamp(request.getTimestamp());
        kafkaTemplate.send(TOPIC_MESSAGE_SEND_RESPONSE, JSONObject.toJSONString(message));
    }

    protected void sendMessagePush(MessageSendJsonRequest request, List<Long> memberIds) {
        for (Long memberId : memberIds) {
            if (!Objects.equals(memberId, request.getFromId())) {
                sendMessagePush(request, memberId);
            }
        }
    }

    protected void sendMessagePush(MessageSendJsonRequest request, Long memberId) {
        log.debug(request.toJsonStr());
        MessageJsonPush message = new MessageJsonPush();
        message.setMessageId(request.getMessageId());
        message.setChatType(request.getChatType());
        message.setFromId(request.getFromId());
        message.setToId(memberId);
        message.setChatId(request.getChatId());
        message.setMessageType(request.getMessageType());
        message.setContent(request.getContent());
        message.setSequence(request.getSequence());
        message.setTimestamp(request.getTimestamp());
        kafkaTemplate.send(TOPIC_MESSAGE_PUSH, JSONObject.toJSONString(message));
    }

    protected void sendMessageFetch(Long chatId, List<Long> memberIds) {
        for (Long memberId : memberIds) {
            sendMessageFetch(chatId, memberId);
        }
    }

    protected void sendMessageFetch(Long chatId, Long toId) {
        MessageJsonFetch message = new MessageJsonFetch();
        message.setToId(toId);
        message.setChatType(Constants.CHAT_TYPE_C2G);
        message.setChatId(chatId);
        kafkaTemplate.send(TOPIC_MESSAGE_FETCH, JSONObject.toJSONString(message));
    }
}
