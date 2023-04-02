package com.ruyuan2020.im.gateway.route.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.im.domain.MessageSendJsonResponse;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageSendResponse;
import com.ruyuan2020.im.gateway.client.ClientManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSendClientCommandHandler extends AbstractClientCommandHandler {

    private final ClientManager clientManager;

    @Override
    public void handleProtoCommand(Command command, ChannelHandlerContext ctx) {
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        clientChannel.writeAndFlush(command);
    }

    @SneakyThrows
    @Override
    public void handleJsonCommand(Command command, ChannelHandlerContext ctx) {
        MessageSendResponse body = MessageSendResponse.parseFrom(command.getBody());
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        JsonCommand jsonCommand = JsonCommand.convert(command);

        MessageSendJsonResponse data = new MessageSendJsonResponse();
        data.setChatId(body.getChatId());
        data.setChatType(body.getChatType());
        data.setFromId(body.getFromId());
        data.setToId(body.getToId());
        data.setMessageId(body.getMessageId());
        data.setSequence(body.getSequence());
        data.setTimestamp(body.getTimestamp());

        jsonCommand.setBody(JSONUtil.parseObj(data));
        clientChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(jsonCommand)));
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_SEND;
    }
}
