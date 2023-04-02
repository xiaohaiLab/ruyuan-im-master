package com.ruyuan2020.im.gateway.server.tcp;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.client.ClientManager;
import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import com.ruyuan2020.im.gateway.server.command.ServerCommandHandler;
import com.ruyuan2020.im.gateway.server.command.ServerCommandHandlerFactory;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import io.netty.channel.socket.SocketChannel;

import java.util.Optional;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<Command> {

    private final ServerCommandHandlerFactory serverCommandHandlerFactory;

    private final ClientManager clientManager;

    private final GatewayContext context;

    private final ConfigProperties configProperties;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 控制在线客户端数量，保证服务稳定
        if (context.onlineCount() > configProperties.getMaxOnlineCount()) {
            ctx.close();
        } else {
            log.info("客户端连接已建立");
            super.channelActive(ctx);
        }
    }

    /**
     * 客户端连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端连接断开");
        offline(ctx);
    }


    @SneakyThrows
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) {
        int type = command.getType();
        ServerCommandHandler commandHandler = serverCommandHandlerFactory.getCommandHandler(type);
        commandHandler.handleCommand(command, ctx);
    }

    /**
     * 客户端心跳检查超时
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                SocketChannel clientChannel = (SocketChannel) ctx.channel();
                Optional<ClientManager.ClientInstance> optional = clientManager.getClientId(clientChannel);
                optional.ifPresent(clientInstance -> log.info("心跳超时:{}", clientInstance.clientId()));
                offline(ctx);
            }
        }
    }

    private void offline(ChannelHandlerContext ctx) {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        Optional<ClientManager.ClientInstance> optional = clientManager.removeChannel(socketChannel);
        optional.ifPresent(clientInstance -> {
            context.offline();
            ServerCommandHandler commandHandler = serverCommandHandlerFactory.getCommandHandler(CommandType.COMMAND_OFFLINE);
            Command command = Command.newBuilder()
                    .setUserId(clientInstance.getUserId())
                    .setClient(clientInstance.getClient())
                    .setType(CommandType.COMMAND_OFFLINE)
                    .build();
            commandHandler.handleCommand(command, ctx);
        });

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
