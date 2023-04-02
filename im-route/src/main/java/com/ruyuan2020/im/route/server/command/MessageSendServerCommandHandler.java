package com.ruyuan2020.im.route.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageSendRequest;
import com.ruyuan2020.im.route.util.RouteContext;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSendServerCommandHandler implements ServerCommandHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("#{'topic-c2c-message-send-'.concat('${spring.profiles.active}')}")
    private String TOPIC_C2C_MESSAGE_SEND;

    @Value("#{'topic-c2g-message-send-'.concat('${spring.profiles.active}')}")
    private String TOPIC_C2G_MESSAGE_SEND;

    private final Executor taskExecutor;

    private final RedisTemplate<String, Long> redisTemplate;

    private final RouteContext routeContext;

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        taskExecutor.execute(() -> {
            try {
                MessageSendRequest body = MessageSendRequest.parseFrom(command.getBody());

                MessageSendJsonRequest request = new MessageSendJsonRequest();
                request.setMessageId(body.getMessageId());
                request.setChatType(body.getChatType());
                request.setFromId(body.getFromId());
                request.setToId(body.getToId());
                request.setChatId(body.getChatId());
                request.setMessageType(body.getMessageType());
                request.setContent(body.getContent());
                request.setTimestamp(body.getTimestamp());
                if (Constants.CHAT_TYPE_C2G.equals(request.getChatType())) {
                    // 使用 redis 获取当前服务的 sequence
                    Optional<Long> sequenceOptional = Optional.ofNullable(redisTemplate.boundValueOps(Constants.REDIS_SEQ_KEY + "::" + routeContext.serverId()).increment());
                    // 群聊服务端设置sequence
                    request.setSequence(sequenceOptional.orElse(1L));
                    kafkaTemplate.send(TOPIC_C2G_MESSAGE_SEND, String.valueOf(request.getChatId()), JSONUtil.toJsonStr(request));
                    log.info(JSONUtil.toJsonStr(request));
                } else {
                    request.setSequence(body.getSequence());
                    kafkaTemplate.send(TOPIC_C2C_MESSAGE_SEND, String.valueOf(request.getChatId()), JSONUtil.toJsonStr(request));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_SEND;
    }
}
