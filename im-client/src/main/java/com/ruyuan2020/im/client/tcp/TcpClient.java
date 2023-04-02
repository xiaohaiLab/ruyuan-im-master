package com.ruyuan2020.im.client.tcp;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.client.constant.ClientConstants;
import com.ruyuan2020.im.client.http.IpListRemote;
import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.client.retry.RetryCommandExecutor;
import com.ruyuan2020.im.client.retry.RetryMessageExecutor;
import com.ruyuan2020.im.client.util.TokenHolder;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.common.im.util.AddressUtils;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.MessageSendRequest;
import com.ruyuan2020.im.common.protobuf.OnlineRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author case
 */
@Slf4j
public class TcpClient {

    private static final EventLoopGroup threadGroup = new NioEventLoopGroup();

    private final IpListRemote ipListRemote = new IpListRemote(this);

    private CommandListener commandListener;

    private AddressInstance addressInstance;

    private final int CONNECT_MAX_RETRY = PropertiesUtils.getConnectMaxRetry() == -1 ? Integer.MAX_VALUE : PropertiesUtils.getConnectMaxRetry();

    ScheduledFuture<?> scheduledFuture;

    ChannelHandlerContext ctx;

    RetryCommandExecutor retryCommandExecutor;

    RetryMessageExecutor retryMessageExecutor;

    MessageSet messageSet;

    public TcpClient() {
        this.messageSet = new MessageSet();
        this.retryCommandExecutor = new RetryCommandExecutor(this);
        this.retryMessageExecutor = new RetryMessageExecutor(this);
    }

    public void connect(String token, Long userId, CommandListener commandListener) {
        this.commandListener = commandListener;
        TokenHolder.TokenInfo tokenInfo = new TokenHolder.TokenInfo(token, userId);
        TokenHolder.setTokenInfo(this, tokenInfo);
        reconnect();
    }

    @SneakyThrows
    void reconnect() {
        Bootstrap client = create();
        int retry = 0;
        while (retry < CONNECT_MAX_RETRY) {
            try {
                if (StrUtil.isNotBlank(PropertiesUtils.getImGatewayAddress())) {
                    addressInstance = AddressUtils.parseAddress(PropertiesUtils.getImGatewayAddress());
                } else {
                    JsonResult<?> jsonResult = ipListRemote.get();
                    if (jsonResult.getSuccess()) {
                        addressInstance = JSONUtil.toBean((JSONObject) jsonResult.getData(), AddressInstance.class);
                    } else throw new SystemException(jsonResult.getErrorMessage());
                }
                // 连接路由服务
                ChannelFuture channelFuture = client.connect(addressInstance.getIp(), addressInstance.getPort()).sync();
                if (channelFuture.isSuccess()) {
                    log.debug("与网关[{}]的连接已建立", addressInstance.getServerId());
                    // 注册网关到路由服务
                    online((SocketChannel) channelFuture.channel());
                    return;
                }
            } catch (Exception e) {
                log.error("网关连接失败重试", e);
                Thread.sleep(3000);
                retry++;
            }
        }
        throw new SystemException("与网关建立连接失败");
    }

    /**
     * 发送命令
     */
    @SneakyThrows
    public void sendCommand(Command command) {
        ctx.writeAndFlush(command);
    }

    /**
     * 发送命令，支持重试
     */
    public void sendCommand(Command command, CommandListener failedListener) {
        try {
            // 发送命令
            sendCommand(command);
            if (command.getType() == CommandType.COMMAND_MESSAGE_SEND) {
                // 如果命令是发送消息
                MessageSendRequest request = MessageSendRequest.parseFrom(command.getBody());
                if (messageSet.exist(request.getMessageId())) {
                    log.info("重新发送消息[messageId:{},chatType:{},messageType:{},content:{}]给[{}]", request.getMessageId(), request.getChatType(), request.getMessageType(), request.getContent(), request.getToId());
                }
                // 本地缓存客户端消息id
                messageSet.setMessage(request.getMessageId());
                // 放入消息重试队列
                retryMessageExecutor.enqueue(command, request.getMessageId(), failedListener);
            }
        } catch (Exception e) {
            retryCommandExecutor.enqueue(command, null, failedListener);
        }
    }

    public void close() {
        retryCommandExecutor.stop();
        retryMessageExecutor.stop();
        if (Objects.nonNull(scheduledFuture) && scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        if (Objects.nonNull(ctx)) {
            ctx.close();
            ctx = null;
        }
        threadGroup.shutdownGracefully();
    }

    /**
     * 处理网关服务发送来的命令
     */
    void handleServerCommand(Command command) {
        commandListener.onCommand(command);
    }

    public boolean waitAck(Long messageId) {
        return messageSet.exist(messageId);
    }

    /**
     * 创建Bootstrap
     */
    private Bootstrap create() {
        Bootstrap client = new Bootstrap();
        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .handler(new TcpClientChannelInitializer(this));
        return client;
    }

    /**
     * 连接成功后向网关服务端发送上线消息
     */
    private void online(SocketChannel socketChannel) {
        String token = TokenHolder.getTokenInfo(this).getToken();
        Long userId = TokenHolder.getTokenInfo(this).getUserId();
        OnlineRequest body = OnlineRequest.newBuilder().setToken(token).build();
        Command newCommand = Command.newBuilder()
                .setType(CommandType.COMMAND_ONLINE)
                .setUserId(userId)
                .setClient(ClientConstants.CLIENT_ID)
                .setBody(body.toByteString())
                .build();
        socketChannel.writeAndFlush(newCommand);
        log.debug("[{}:{}]通知网关[{}]上线...", userId, ClientConstants.CLIENT_ID, addressInstance.getServerId());
    }

    public AddressInstance getAddressInstance() {
        return addressInstance;
    }

    static class MessageSet {

        private final Set<Long> messageSet = Collections.synchronizedSet(new HashSet<>());

        void setMessage(Long messageId) {
            messageSet.add(messageId);
        }

        void clearMessage(Long messageId) {
            messageSet.remove(messageId);
        }

        Boolean exist(Long messageId) {
            return messageSet.contains(messageId);
        }
    }
}
