package com.ruyuan2020.im.route.server.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.route.gateway.GatewayRegistry;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HeartbeatServerCommandHandler implements ServerCommandHandler {

    private final GatewayRegistry gatewayRegistry;

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        String gatewayId = gatewayRegistry.getGatewayId(ctx);
        log.info("收到[{}]的心跳", gatewayId);
        ctx.writeAndFlush(command);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_HEARTBEAT;
    }
}
