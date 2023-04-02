package com.ruyuan2020.im.gateway.route;

import cn.hutool.core.util.StrUtil;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.common.im.util.AddressUtils;
import com.ruyuan2020.im.common.im.util.ChannelUtils;
import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.route.strategy.RoundRobinRouteStrategy;
import com.ruyuan2020.im.gateway.route.strategy.RouteStrategy;
import com.ruyuan2020.im.gateway.util.GatewayContext;
import com.ruyuan2020.im.gateway.util.SpringContextUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理与路由系统的长连接
 */
@Slf4j
@Component
@RequiredArgsConstructor
@DependsOn("springContextUtils")
public class RouteTransport {

    private final GatewayContext context;

    private final ConcurrentHashMap<String, RouteClient> clients = new ConcurrentHashMap<>();

    private final ZkClient zkClient;

    public void init() {
        connectRouteServer();
    }

    public ChannelFuture send(Command command, Long routeKey) {
        return send(command, routeKey, RoundRobinRouteStrategy.class);
    }

    public ChannelFuture send(Command command, Long routeKey, Class<? extends RouteStrategy> strategyClass) {
        // 获取策略
        RouteStrategy routeStrategy = SpringContextUtils.getBean(strategyClass);
        // 通过策略获取路由服务的客户端
        RouteClient routeClient = routeStrategy.routeServer(new ArrayList<>(clients.values()), routeKey);


        return routeClient.send(command);
    }

    public Optional<RouteClient> get(SocketChannel routeChannel) {
        String channelId = ChannelUtils.getChannelId(routeChannel);
        return Optional.ofNullable(clients.get(channelId));
    }

    public RouteClient remove(String channelId) {
        return clients.remove(channelId);
    }

    private void connectRouteServer() {
        String path = context.getZkRouteRootPath();
        // 通过zk获取路由服务信息
        zkClient.createNode(path);
        zkClient.addListener(path, (type, oldData, data) -> {
            try {
                if (type == CuratorCacheListener.Type.NODE_CREATED) {
                    if (!StrUtil.equals(path, data.getPath())) {
                        String childPath = data.getPath().replace(path + "/", "");
                        AddressInstance addressInstance = AddressUtils.parseAddress(childPath);
                        log.info("路由服务[{}]上线", addressInstance.getServerId());
                        connect(addressInstance);
                    }
                } else if (type == CuratorCacheListener.Type.NODE_DELETED) {
                    String childPath = oldData.getPath().replace(path + "/", "");
                    AddressInstance addressInstance = AddressUtils.parseAddress(childPath);
                    log.info("路由服务[{}]下线", addressInstance.getServerId());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 连接路由服务
     */
    private void connect(AddressInstance routeAddressInstance) {
        RouteClient routeClient = new RouteClient(routeAddressInstance);
        String serverId = context.serverId();
        routeClient.connect(serverId, routeChannel -> {
            String channelId = ChannelUtils.getChannelId(routeClient.getChannel());
            clients.put(channelId, routeClient);
        });
    }
}
