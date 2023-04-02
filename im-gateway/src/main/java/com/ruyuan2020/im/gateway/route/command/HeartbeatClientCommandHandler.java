package com.ruyuan2020.im.gateway.route.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.route.RouteClient;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatClientCommandHandler extends AbstractClientCommandHandler {

    private final RouteTransport routeTransport;

    @Override
    public void handleProtoCommand(Command command, ChannelHandlerContext ctx) {
        Optional<RouteClient> optional = routeTransport.get((SocketChannel) ctx.channel());
        optional.ifPresent(routeClient -> log.info("收到[{}]的心跳响应", routeClient.getAddressInstance().getServerId()));
    }

    @Override
    public void handleJsonCommand(Command command, ChannelHandlerContext ctx) {
        handleProtoCommand(command, ctx);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_HEARTBEAT;
    }
}
