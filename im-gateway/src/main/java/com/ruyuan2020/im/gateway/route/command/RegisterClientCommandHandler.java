package com.ruyuan2020.im.gateway.route.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.route.RouteClient;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 向路由服务注册成功后的处理
 *
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterClientCommandHandler extends AbstractClientCommandHandler {

    private final RouteTransport routeTransport;

    private final ConfigProperties configProperties;

    @SneakyThrows
    @Override
    public void handleProtoCommand(Command command, ChannelHandlerContext ctx) {
        Optional<RouteClient> optional = routeTransport.get((SocketChannel) ctx.channel());
        optional.ifPresent(routeClient -> {
            log.info("向路由服务[{}]注册成功...", routeClient.getAddressInstance().getServerId());
            // 设置ChannelHandlerContext
            routeClient.setContext(ctx);
            // 开启心跳调度
            ScheduledFuture<?> scheduledFuture = routeClient.getContext().executor().scheduleAtFixedRate(() -> {
                if (routeClient.getChannel().isActive()) {
                    log.info("发送心跳给[{}]", routeClient.getAddressInstance().getServerId());
                    Command newCommand = Command.newBuilder().setType(CommandType.COMMAND_HEARTBEAT).build();
                    ctx.writeAndFlush(newCommand);
                }
            }, 0, configProperties.getHeartbeat().getHeartbeatInterval(), TimeUnit.MILLISECONDS);
            routeClient.setScheduledFuture(scheduledFuture);
        });
    }

    @Override
    public void handleJsonCommand(Command command, ChannelHandlerContext ctx) {
        handleProtoCommand(command, ctx);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_REGISTER;
    }
}
