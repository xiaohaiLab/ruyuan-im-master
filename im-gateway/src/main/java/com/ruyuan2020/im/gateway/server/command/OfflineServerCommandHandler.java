package com.ruyuan2020.im.gateway.server.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OfflineServerCommandHandler implements ServerCommandHandler {

    private final RouteTransport routeTransport;

    @Override
    public void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx) {
        int type = jsonCommand.getType();
        Command command = Command.newBuilder()
                .setUserId(jsonCommand.getUserId())
                .setClient(jsonCommand.getClient())
                .setType(type)
                .build();
        handleCommand(command, ctx);
    }

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        log.info("客户端下线：{}", command.getUserId() + ":" + command.getClient());
        routeTransport.send(command, command.getUserId());
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_OFFLINE;
    }
}
