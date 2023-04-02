package com.ruyuan2020.im.gateway.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.im.domain.OnlineJsonRequest;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.OnlineRequest;
import com.ruyuan2020.im.gateway.client.ClientManager;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import io.netty.channel.socket.SocketChannel;

/**
 * @author case
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OnlineServerCommandHandler implements ServerCommandHandler {

    private final RouteTransport routeTransport;

    private final ClientManager clientManager;

    private final GatewayContext context;

    @Override
    public void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx) {
        OnlineJsonRequest request = JSONUtil.toBean(jsonCommand.getBody(), OnlineJsonRequest.class);
        String token = request.getToken();
        OnlineRequest body = OnlineRequest.newBuilder()
                .setToken(token)
                .build();
        Command command = Command.newBuilder()
                .setType(jsonCommand.getType())
                .setUserId(jsonCommand.getUserId())
                .setClient(jsonCommand.getClient())
                .setBody(body.toByteString()).build();
        handleCommand(command, ctx);
    }

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        log.info("客户端上线：{}", command.getUserId() + ":" + command.getClient());
        ChannelFuture future = routeTransport.send(command, command.getUserId());
        future.addListener((ChannelFutureListener) channelFuture -> {
            clientManager.addChannel(command.getUserId(), command.getClient(), (SocketChannel) ctx.channel());
            context.online();
        });
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_ONLINE;
    }
}
