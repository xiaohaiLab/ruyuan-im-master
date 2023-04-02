package com.ruyuan2020.im.gateway.server.command;

import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.protobuf.Command;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author case
 */
public interface ServerCommandHandler {

    void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx);

    void handleCommand(Command command, ChannelHandlerContext ctx);

    int getType();
}
