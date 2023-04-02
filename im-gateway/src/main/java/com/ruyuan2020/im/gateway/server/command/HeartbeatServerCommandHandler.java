package com.ruyuan2020.im.gateway.server.command;

import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.JsonCommand;
import com.ruyuan2020.im.common.protobuf.Command;

import com.ruyuan2020.im.common.protobuf.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@RequiredArgsConstructor
@Component
public class HeartbeatServerCommandHandler implements ServerCommandHandler {

    @Override
    public void handleCommand(JsonCommand jsonCommand, ChannelHandlerContext ctx) {
        JsonCommand newCommand = new JsonCommand();
        newCommand.setType(jsonCommand.getType());
        newCommand.setUserId(jsonCommand.getUserId());
        newCommand.setClient(jsonCommand.getClient());
        ctx.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(newCommand)));
    }

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        Result result = Result.newBuilder()
                .setSuccess(true)
                .build();
        Command newCommand = Command.newBuilder()
                .setUserId(command.getUserId())
                .setClient(command.getClient())
                .setType(command.getType())
                .setBody(result.toByteString()).build();
        ctx.writeAndFlush(newCommand);
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_HEARTBEAT;
    }
}
