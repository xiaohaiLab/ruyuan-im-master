package com.ruyuan2020.im.route.server;

import com.ruyuan2020.im.common.im.constant.ZkConstants;
import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.route.util.RouteContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 路由服务
 *
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RouteServer {

    private final RouteContext context;

    private final RouteServerChannelInitializer routeServerChannelInitializer;

    private final ZkClient zkClient;

    @SneakyThrows
    public void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 16 * 1024)
                .childOption(ChannelOption.SO_SNDBUF, 16 * 1024)
                .childHandler(routeServerChannelInitializer);

        bootstrap.bind(context.getPort()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                // 路由服务启动成功后，在zk上创建临时节点
                log.info("在zk上创建临时节点");
                zkClient.createEphemeralNode(context.getZkPath());
                log.info("路由服务[{}]正在运行", context.serverId());
            } else {
                channelFuture.channel().close();
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }).sync();
    }
}
