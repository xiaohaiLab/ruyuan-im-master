package com.ruyuan2020.im.route.server.command;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.core.util.DateTimeUtils;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.OnlineRequest;
import com.ruyuan2020.im.common.protobuf.Result;
import com.ruyuan2020.im.route.gateway.GatewayRegistry;
import com.ruyuan2020.im.route.domain.Session;
import com.ruyuan2020.im.route.properties.ConfigProperties;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OnlineServerCommandHandler implements ServerCommandHandler {

    private final DefaultTokenServices tokenServices;

    private final GatewayRegistry gatewayRegistry;

    private final RedisTemplate<String, Session> redisTemplate;

    private final Executor taskExecutor;

    private final ConfigProperties configProperties;

    @SneakyThrows
    @Override
    public void handleCommand(Command command, ChannelHandlerContext ctx) {
        taskExecutor.execute(() -> {
            try {
                String gatewayId = gatewayRegistry.getGatewayId(ctx);
                OnlineRequest onlineRequest = OnlineRequest.parseFrom(command.getBody());
                long userId = command.getUserId();
                int client = command.getClient();

                boolean authResult = true;
                if (configProperties.getAuthenticate()) {
                    // 认证token
                    try {
                        tokenServices.loadAuthentication(onlineRequest.getToken());
                    } catch (InvalidTokenException | AuthenticationException e) {
                        authResult = false;
                    }
                }
                if (authResult) {
                    // 认证成功保存Session到redis
                    Session session = new Session();
                    session.setGatewayId(gatewayId);
                    session.setTimestamp(DateTimeUtils.currentDateTime());
                    session.setClient(client);
                    session.setUserId(userId);
                    redisTemplate.boundHashOps(Constants.REDIS_SESSION_KEY + "::" + userId).put(String.valueOf(client), session);
                }

                Command newCommand = getCommand(command, authResult);
                // 把writeAndFlush操作放入eventLoop中执行，避免线程切换
                ctx.executor().execute(() -> ctx.writeAndFlush(newCommand));
            } catch (Exception e) {
                Command newCommand = getCommand(command, false);
                ctx.executor().execute(() -> ctx.writeAndFlush(newCommand));
                log.error(e.getMessage(), e);
            }
        });
    }

    private Command getCommand(Command command, boolean result) {
        return Command.newBuilder()
                .setType(command.getType())
                .setUserId(command.getUserId())
                .setClient(command.getClient())
                .setBody(Result.newBuilder()
                        .setSuccess(result)
                        .build().toByteString())
                .build();
    }

    @Override
    public int getType() {
        return CommandType.COMMAND_ONLINE;
    }
}
