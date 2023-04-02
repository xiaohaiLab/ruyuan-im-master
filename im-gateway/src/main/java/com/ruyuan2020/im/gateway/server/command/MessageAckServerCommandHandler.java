package com.ruyuan2020.im.gateway.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.im.domain.MessageAckJsonRequest;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageAckRequest;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@RequiredArgsConstructor
@Component
public class MessageAckServerCommandHandler implements ServerCommandHandler {

    private final RouteTransport routeTransport;

    @Override
    public void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx) {
        MessageAckJsonRequest request = JSONUtil.toBean(jsonCommand.getBody(), MessageAckJsonRequest.class);
        MessageAckRequest body = MessageAckRequest.newBuilder()
                .setChatType(request.getChatType())
                .setChatId(request.getChatId())
                .setMemberId(request.getMemberId())
                .setMessageId(request.getMessageId())
                .build();

        Command command = Command.newBuilder()
                .setType(jsonCommand.getType())
                .setUserId(jsonCommand.getUserId())
                .setBody(body.toByteString()).build();
        handleCommand(command, ctx);
    }

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        routeTransport.send(command, command.getUserId());
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_ACK;
    }
}
