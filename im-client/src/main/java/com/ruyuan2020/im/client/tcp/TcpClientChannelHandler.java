package com.ruyuan2020.im.client.tcp;

import com.ruyuan2020.im.common.protobuf.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.UnknownHostException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TcpClientChannelHandler extends SimpleChannelInboundHandler<Command> {

    private final CommandHandlerFactory commandHandlerFactory = CommandHandlerFactory.getInstance();

    private final TcpClient tcpClient;

    /**
     * 路由服务连接被断开，删除客户端
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        tcpClient.close();
        tcpClient.reconnect();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Command command) {
        try {
            int type = command.getType();
            CommandHandler commandHandler = commandHandlerFactory.getCommandHandler(type);
            if (Objects.nonNull(commandHandler)) {
                commandHandler.handleCommand(command, ctx, tcpClient);
            } else {
                tcpClient.handleServerCommand(command);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws UnknownHostException {
        if (cause instanceof UnknownHostException) {
            throw (UnknownHostException) cause;
        }
        log.error(cause.getMessage(), cause);
    }
}
