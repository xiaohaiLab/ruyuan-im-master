package com.ruyuan2020.im.route.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.MessageAckJsonRequest;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageAckRequest;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageAckServerCommandHandler implements ServerCommandHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("#{'topic-c2c-message-ack-'.concat('${spring.profiles.active}')}")
    private String TOPIC_C2C_MESSAGE_ACK;

    @Value("#{'topic-c2g-message-ack-'.concat('${spring.profiles.active}')}")
    private String TOPIC_C2G_MESSAGE_ACK;

    private final Executor taskExecutor;


    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        taskExecutor.execute(() -> {
            try {
                MessageAckRequest body = MessageAckRequest.parseFrom(command.getBody());

                MessageAckJsonRequest request = new MessageAckJsonRequest();
                request.setChatType(body.getChatType());
                request.setChatId(body.getChatId());
                request.setMemberId(body.getMemberId());
                request.setClientId(command.getClient());
                request.setMessageId(body.getMessageId());
                if (Constants.CHAT_TYPE_C2G.equals(request.getChatType())) {
                    kafkaTemplate.send(TOPIC_C2G_MESSAGE_ACK, JSONUtil.toJsonStr(request));
                } else {
                    kafkaTemplate.send(TOPIC_C2C_MESSAGE_ACK, JSONUtil.toJsonStr(request));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_ACK;
    }
}
