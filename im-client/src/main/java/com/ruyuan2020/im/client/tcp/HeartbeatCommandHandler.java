package com.ruyuan2020.im.client.tcp;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.protobuf.Command;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author case
 */
@Slf4j
public class HeartbeatCommandHandler extends AbstractCommandHandler {

    public HeartbeatCommandHandler(ThreadPoolExecutor executor) {
        super(executor);
    }

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx, TcpClient tcpClient) {
        log.debug("收到[{}]的心跳响应", tcpClient.getAddressInstance().getServerId());
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_HEARTBEAT;
    }
}
