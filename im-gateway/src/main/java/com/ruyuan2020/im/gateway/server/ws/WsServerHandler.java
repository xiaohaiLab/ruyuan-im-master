package com.ruyuan2020.im.gateway.server.ws;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.gateway.client.ClientManager;
import com.ruyuan2020.im.gateway.server.command.ServerCommandHandler;
import com.ruyuan2020.im.gateway.server.command.ServerCommandHandlerFactory;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
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
public class WsServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ServerCommandHandlerFactory serverCommandHandlerFactory;

    private final ClientManager clientManager;

    private final GatewayContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接已建立");
        super.channelActive(ctx);
    }

    /**
     * 客户端连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端连接断开");
        offline(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        String jsonStr = frame.text();
        JsonCommand jsonCommand = JSONUtil.toBean(jsonStr, JsonCommand.class);
        int type = jsonCommand.getType();
        ServerCommandHandler commandHandler = serverCommandHandlerFactory.getCommandHandler(type);
        commandHandler.handleCommand(jsonCommand, ctx);
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
            JsonCommand jsonCommand = new JsonCommand();
            jsonCommand.setUserId(clientInstance.getUserId());
            jsonCommand.setClient(clientInstance.getClient());
            jsonCommand.setType(CommandType.COMMAND_OFFLINE);
            commandHandler.handleCommand(jsonCommand, ctx);
        });
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
