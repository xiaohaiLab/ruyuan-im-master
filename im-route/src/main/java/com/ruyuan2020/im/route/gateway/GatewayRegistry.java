package com.ruyuan2020.im.route.gateway;

import com.ruyuan2020.im.common.im.util.ChannelUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class GatewayRegistry {

    // gatewayId to ChannelHandlerContext
    private final ConcurrentHashMap<String, ChannelHandlerContext> registry = new ConcurrentHashMap<>();

    // channelId to gatewayId
    private final ConcurrentHashMap<String, String> channelIdToGateway = new ConcurrentHashMap<>();

    public void register(String gatewayId, ChannelHandlerContext ctx) {
        String channelId = ChannelUtils.getChannelId((SocketChannel) ctx.channel());
        channelIdToGateway.put(channelId, gatewayId);
        registry.put(gatewayId, ctx);
    }

    public String remove(ChannelHandlerContext ctx) {
        String channelId = ChannelUtils.getChannelId((SocketChannel) ctx.channel());
        String gatewayId = channelIdToGateway.remove(channelId);
        registry.remove(gatewayId);
        return gatewayId;
    }

    public String getGatewayId(ChannelHandlerContext ctx) {
        return channelIdToGateway.get(ChannelUtils.getChannelId((SocketChannel) ctx.channel()));
    }

    public ChannelHandlerContext getChannel(String gatewayId) {
        return registry.get(gatewayId);
    }
}
