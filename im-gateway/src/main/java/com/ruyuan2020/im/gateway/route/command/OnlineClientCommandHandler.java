package com.ruyuan2020.im.gateway.route.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.Result;
import com.ruyuan2020.im.gateway.client.ClientManager;
import com.ruyuan2020.im.gateway.util.ResultConverter;
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
public class OnlineClientCommandHandler extends AbstractClientCommandHandler {

    private final ClientManager clientManager;

    @SneakyThrows
    @Override
    public void handleProtoCommand(Command command, ChannelHandlerContext ctx) {
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        Result result = Result.parseFrom(command.getBody());
        handleResult(result, clientChannel);
        clientChannel.writeAndFlush(command);
    }

    @SneakyThrows
    @Override
    public void handleJsonCommand(Command command, ChannelHandlerContext ctx) {
        SocketChannel clientChannel = clientManager.getChannel(command.getUserId(), command.getClient());
        Result result = Result.parseFrom(command.getBody());
        handleResult(result, clientChannel);
        JsonCommand jsonCommand = JsonCommand.convert(command);
        jsonCommand.setBody(JSONUtil.parseObj(ResultConverter.convert(result)));
        clientChannel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(jsonCommand)));
    }

    @SneakyThrows
    private void handleResult(Result result, SocketChannel clientChannel) {
        if (!result.getSuccess()) {
            clientManager.removeChannel(clientChannel);
        }
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_ONLINE;
    }
}
