package com.ruyuan2020.im.gateway.route;

import com.ruyuan2020.im.common.im.constant.CommandType;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.common.protobuf.RegisterRequest;
import com.ruyuan2020.im.gateway.route.strategy.consistent.Node;
import com.ruyuan2020.im.gateway.util.SpringContextUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class RouteClient implements Node {

    private final AddressInstance addressInstance;

    private final Bootstrap client;

    private final EventLoopGroup threadGroup;

    private ChannelHandlerContext ctx;

    private SocketChannel routeChannel;

    private ScheduledFuture<?> scheduledFuture;

    public RouteClient(AddressInstance addressInstance) {
        this.addressInstance = addressInstance;
        client = new Bootstrap();
        threadGroup = new NioEventLoopGroup();
        client.group(threadGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .handler(SpringContextUtils.getBean(RouteClientChannelInitializer.class));
    }

    @SneakyThrows
    public void connect(String serverId, Consumer<SocketChannel> consumer) {
        // 连接路由服务
        client.connect(addressInstance.getIp(), addressInstance.getPort()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("与路由服务的连接已建立");
                routeChannel = (SocketChannel) channelFuture.channel();
                // 执行连接后的回调
                consumer.accept(routeChannel);
                // 注册网关到路由服务
                register(serverId);
            } else {
                channelFuture.channel().close();
                threadGroup.shutdownGracefully();
            }
        }).sync();
    }

    public ChannelFuture send(Command command) {
        return ctx.writeAndFlush(command);
    }

    public void close() {
        if (Objects.nonNull(scheduledFuture) && scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        if (Objects.nonNull(ctx)) {
            ctx.close();
        }
        threadGroup.shutdownGracefully();
    }

    /**
     * 连接成功后向路由服务端发起注册
     *
     * @param serverId 网关服务id
     */
    private void register(String serverId) {
        RegisterRequest body = RegisterRequest.newBuilder().setServerId(serverId).build();
        Command newCommand = Command.newBuilder()
                .setType(CommandType.COMMAND_REGISTER)
                .setBody(body.toByteString())
                .build();
        routeChannel.writeAndFlush(newCommand);
        log.info("[{}]向路由服务[{}]发起注册...", serverId, addressInstance.getServerId());
    }

    public ChannelHandlerContext getContext() {
        return ctx;
    }

    public void setContext(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public SocketChannel getChannel() {
        return routeChannel;
    }

    public AddressInstance getAddressInstance() {
        return addressInstance;
    }

    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    @Override
    public String getKey() {
        return addressInstance.getServerId();
    }
}
