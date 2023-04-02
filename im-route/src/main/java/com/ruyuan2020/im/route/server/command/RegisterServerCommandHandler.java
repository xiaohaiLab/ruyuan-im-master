package com.ruyuan2020.im.route.server.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.RegisterRequest;
import com.ruyuan2020.im.route.gateway.GatewayRegistry;
import io.netty.channel.ChannelHandlerContext;
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
public class RegisterServerCommandHandler implements ServerCommandHandler {

    private final GatewayRegistry gatewayRegistry;

    @SneakyThrows
    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        RegisterRequest body = RegisterRequest.parseFrom(command.getBody());
        String gatewayId = body.getServerId();
        log.info("接收到网关服务[{}]注册信息...", gatewayId);
        // 注册
        gatewayRegistry.register(gatewayId, ctx);
        ctx.writeAndFlush(command);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_REGISTER;
    }
}
