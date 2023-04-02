package com.ruyuan2020.im.gateway.route.command;

import com.ruyuan2020.im.common.protobuf.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

import java.util.concurrent.Future;

/**
 * @author case
 */
public interface ClientCommandHandler {

    void handleCommand(Command command, ChannelHandlerContext ctx);

    int getType();
}
