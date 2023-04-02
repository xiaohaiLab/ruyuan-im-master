package com.ruyuan2020.im.gateway.server;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import com.ruyuan2020.im.gateway.route.RouteTransport;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayServer {

    private final ConfigProperties configProperties;

    private final GatewayContext context;

    private final Map<String, ChannelInitializer<Channel>> initializerMap;

    private final RouteTransport routeTransport;

    private final ZkClient zkClient;

    private EventLoopGroup boss;

    private EventLoopGroup worker;

    @SneakyThrows
    public void start() {
        // 初始化路由通信组件
        routeTransport.init();

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        // 根据protocol协议获取对应的ChannelInitializer
        // 支持websocket和tcp
        ChannelInitializer<Channel> initializer = initializerMap.get(configProperties.getProtocol() + ChannelInitializer.class.getSimpleName());
        if (Objects.isNull(initializer)) {
            throw new SystemException("不支持的协议");
        }
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 16 * 1024)
                .childOption(ChannelOption.SO_SNDBUF, 16 * 1024)
                .childHandler(initializer);

        ChannelFuture future = bootstrap.bind(context.getPort())
                .addListener((ChannelFutureListener) bindFuture -> {
                    if (bindFuture.isSuccess()) {
                        // 路由服务启动成功后，在zk上创建临时节点
                        if (context.isTcp()) {
                            log.info("在zk上创建临时节点:{}", context.getZkPath());
                            // 创建节点，设置客户端数量
                            zkClient.createEphemeralNode(context.getZkPath(), "0");
                            context.schedule();
                        }
                        log.info("网关服务[{}]正在运行", context.serverId());
                    } else {
                        bindFuture.channel().closeFuture().sync()
                                .addListener((ChannelFutureListener) closeFuture -> close());
                    }
                }).sync();
        future.channel().closeFuture().sync().addListener((ChannelFutureListener) closeFuture -> {
            close();
        });
    }

    private void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        log.info("网关服务[{}]已停止", context.serverId());
        System.exit(0);
    }
}
