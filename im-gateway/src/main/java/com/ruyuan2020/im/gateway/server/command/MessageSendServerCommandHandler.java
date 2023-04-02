package com.ruyuan2020.im.gateway.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.core.util.DateTimeUtils;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonRequest;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageSendRequest;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import com.ruyuan2020.im.gateway.route.strategy.ConsistentHashRouteStrategy;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MessageSendServerCommandHandler implements ServerCommandHandler {

    private final RouteTransport routeTransport;

    @Override
    public void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx) {
        MessageSendJsonRequest request = JSONUtil.toBean(jsonCommand.getBody(), MessageSendJsonRequest.class);
        MessageSendRequest body = MessageSendRequest.newBuilder()
                .setMessageId(request.getMessageId())
                .setChatType(request.getChatType())
                .setFromId(request.getFromId())
                .setToId(request.getToId())
                .setChatId(request.getChatId())
                .setMessageType(request.getMessageType())
                .setContent(request.getContent())
                .setSequence(request.getSequence())
                .setTimestamp(DateTimeUtils.getSecondTimestamp())
                .build();

        Command command = Command.newBuilder()
                .setType(jsonCommand.getType())
                .setUserId(jsonCommand.getUserId())
                .setClient(jsonCommand.getClient())
                .setBody(body.toByteString()).build();
        handleCommand(command, ctx);
    }

    @SneakyThrows
    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        MessageSendRequest request = MessageSendRequest.newBuilder(MessageSendRequest.parseFrom(command.getBody())).setTimestamp(DateTimeUtils.getSecondTimestamp()).build();
        log.info("发送消息[messageId:{},chatType:{},messageType:{},content:{}]给[{}]", request.getMessageId(), request.getChatType(), request.getMessageType(), request.getContent(), request.getToId());
        Command newCommand = Command.newBuilder(command).setBody(request.toByteString()).build();
        if (Constants.CHAT_TYPE_C2G.equals(request.getChatType())) {
            // 群聊消息使用hash一致策略，将相同的群消息发送到相同的路由服务
            routeTransport.send(newCommand, request.getToId(), ConsistentHashRouteStrategy.class);
        } else {
            routeTransport.send(newCommand, newCommand.getUserId());
        }
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_SEND;
    }
}
