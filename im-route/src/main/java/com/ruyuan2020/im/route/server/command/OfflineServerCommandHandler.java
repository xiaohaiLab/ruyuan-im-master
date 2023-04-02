package com.ruyuan2020.im.route.server.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.route.domain.Session;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OfflineServerCommandHandler implements ServerCommandHandler {

    private final RedisTemplate<String, Session> redisTemplate;

    private final Executor taskExecutor;

    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        taskExecutor.execute(() -> {
            try {
                // 下线后删除session
                redisTemplate.boundHashOps(Constants.REDIS_SESSION_KEY + "::" + command.getUserId()).delete(String.valueOf(command.getClient()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_OFFLINE;
    }
}
