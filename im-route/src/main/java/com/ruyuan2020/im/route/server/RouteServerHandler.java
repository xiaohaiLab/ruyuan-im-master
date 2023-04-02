package com.ruyuan2020.im.route.server;

import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.route.gateway.GatewayRegistry;
import com.ruyuan2020.im.route.server.command.ServerCommandHandler;
import com.ruyuan2020.im.route.server.command.ServerCommandHandlerFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
@ChannelHandler.Sharable
public class RouteServerHandler extends SimpleChannelInboundHandler<Command> {

    private final ServerCommandHandlerFactory serverCommandHandlerFactory;

    private final GatewayRegistry gatewayRegistry;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与网关[{}]的连接已建立", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    /**
     * 网关连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        offline(ctx);
    }

    @SneakyThrows
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command command) {
        int type = command.getType();
        ServerCommandHandler commandHandler = serverCommandHandlerFactory.getCommandHandler(type);
        commandHandler.handleCommand(command, ctx);
    }

    private void offline(ChannelHandlerContext ctx) {
        // 网关服务断开连接，从注册表中剔除
        String gatewayId = gatewayRegistry.remove(ctx);
        ctx.close();
        log.info("与网关[{}]连接已断开", gatewayId);
    }

    /**
     * 网关心跳检查超时
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                String gatewayId = gatewayRegistry.getGatewayId(ctx);
                log.info("心跳超时:{}", gatewayId);
                offline(ctx);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }
}
