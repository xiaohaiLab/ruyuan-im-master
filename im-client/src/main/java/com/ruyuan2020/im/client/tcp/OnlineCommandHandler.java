package com.ruyuan2020.im.client.tcp;

import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.client.constant.ClientConstants;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author case
 */
@Slf4j
public class OnlineCommandHandler extends AbstractCommandHandler {

    private final int heartbeatInterval = PropertiesUtils.getHeartbeatInterval();

    public OnlineCommandHandler(ThreadPoolExecutor executor) {
        super(executor);
    }

    @Override
    @SneakyThrows
    public void handleCommand(Command command, ChannelHandlerContext ctx, TcpClient tcpClient) {
        Result result = Result.parseFrom(command.getBody());
        if (result.getSuccess()) {
            log.info("客户端[{}:{}]在网关[{}]上线成功...", command.getUserId(), ClientConstants.CLIENT_ID, tcpClient.getAddressInstance().getServerId());
            // 开启心跳调度
            ScheduledFuture<?> scheduledFuture = ctx.executor().scheduleAtFixedRate(() -> {
                if (ctx.channel().isActive()) {
                    log.debug("发送心跳给[{}]", tcpClient.getAddressInstance().getServerId());
                    Command newCommand = Command.newBuilder().setType(CommandType.COMMAND_HEARTBEAT).build();
                    ctx.writeAndFlush(newCommand);
                }
            }, 0, heartbeatInterval, TimeUnit.MILLISECONDS);
            tcpClient.ctx = ctx;
            tcpClient.scheduledFuture = scheduledFuture;
            tcpClient.retryCommandExecutor.start();
            tcpClient.retryMessageExecutor.start();
        }
        tcpClient.handleServerCommand(command);

    }

    @Override
    public int getType() {
        return CommandType.COMMAND_ONLINE;
    }
}
