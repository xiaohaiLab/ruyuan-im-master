package com.ruyuan2020.im.gateway.route.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.im.domain.MessageJsonPush;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessagePush;
import com.ruyuan2020.im.gateway.client.ClientManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePushClientCommandHandler extends AbstractClientCommandHandler {

    private final ClientManager clientManager;

    @Override
    public void handleProtoCommand(Command command, ChannelHandlerContext ctx) {
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        clientChannel.writeAndFlush(command);
    }

    @SneakyThrows
    @Override
    public void handleJsonCommand(Command command, ChannelHandlerContext ctx) {
        MessagePush body = MessagePush.parseFrom(command.getBody());

        JsonCommand jsonCommand = JsonCommand.convert(command);
        MessageJsonPush data = new MessageJsonPush();
        data.setChatType(body.getChatType());
        data.setFromId(body.getFromId());
        data.setChatId(body.getChatId());
        data.setMessageId(body.getMessageId());
        data.setMessageType(body.getMessageType());
        data.setContent(body.getContent());
        data.setSequence(body.getSequence());
        data.setTimestamp(body.getTimestamp());
        jsonCommand.setBody(JSONUtil.parseObj(data));
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        if (Objects.nonNull(clientChannel)) {
            clientChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(jsonCommand)));
        }
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_PUSH;
    }
}
