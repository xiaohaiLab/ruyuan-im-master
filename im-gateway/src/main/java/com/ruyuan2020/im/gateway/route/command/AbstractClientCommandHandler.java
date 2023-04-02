package com.ruyuan2020.im.gateway.route.command;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

/**
 * @author case
 */
@NoArgsConstructor
public abstract class AbstractClientCommandHandler implements ClientCommandHandler {

    @Autowired
    protected GatewayContext context;

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        if (context.isWebSocket()) {
            handleJsonCommand(command, ctx);
        } else if (context.isTcp()) {
            handleProtoCommand(command, ctx);
        } else {
            throw new SystemException("不支持的协议");
        }
    }

    public abstract int getType();

    public abstract void handleProtoCommand(Command command, ChannelHandlerContext ctx);

    public abstract void handleJsonCommand(Command command, ChannelHandlerContext ctx);
}
