package com.ruyuan2020.im.gateway.route;

import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.im.util.ChannelUtils;
import com.ruyuan2020.im.gateway.route.command.ClientCommandHandlerFactory;
import com.ruyuan2020.im.gateway.route.command.ClientCommandHandler;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class RouteClientChannelHandler extends SimpleChannelInboundHandler<Command> {

    private final RouteTransport routeTransport;

    private final ClientCommandHandlerFactory clientCommandHandlerFactory;

    /**
     * 路由服务连接被断开，删除客户端
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SocketChannel routeChannel = (SocketChannel) ctx.channel();
        RouteClient routeClient = routeTransport.remove(ChannelUtils.getChannelId(routeChannel));
        routeClient.close();
        log.info("路由服务[{}]的连接已断开", routeClient.getAddressInstance().getServerId());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Command command) {
        try {
            int type = command.getType();
            ClientCommandHandler commandHandler = clientCommandHandlerFactory.getCommandHandler(type);
            commandHandler.handleCommand(command, ctx);
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
