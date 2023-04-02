package com.ruyuan2020.im.client.tcp;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageSendResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author case
 */
@Slf4j
public class MessageSendCommandHandler extends AbstractCommandHandler {

    public MessageSendCommandHandler(ThreadPoolExecutor executor) {
        super(executor);
    }

    @SneakyThrows
    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx, TcpClient tcpClient) {
        MessageSendResponse response = MessageSendResponse.parseFrom(command.getBody());
        tcpClient.messageSet.clearMessage(response.getMessageId());
        tcpClient.handleServerCommand(command);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_MESSAGE_SEND;
    }
}
