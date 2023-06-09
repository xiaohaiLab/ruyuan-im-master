package com.ruyuan2020.im.gateway.client;

import com.ruyuan2020.im.common.im.util.ChannelUtils;
import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理与客户端的长连接
 */
@Slf4j
@Component
public class ClientManager {

    private final ConcurrentHashMap<String, SocketChannel> clients = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ClientInstance> channelId2Client = new ConcurrentHashMap<>();

    public void addChannel(Long userId, Integer client, SocketChannel socketChannel) {
        ClientInstance clientInstance = new ClientInstance(userId, client);
        channelId2Client.put(ChannelUtils.getChannelId(socketChannel), clientInstance);
        clients.put(clientInstance.clientId(), socketChannel);
    }

    public Optional<ClientInstance> removeChannel(SocketChannel socketChannel) {
        String channelId = ChannelUtils.getChannelId(socketChannel);
        if (channelId2Client.containsKey(channelId)) {
            ClientInstance clientInstance = channelId2Client.get(channelId);
            clients.remove(clientInstance.clientId());
            return Optional.ofNullable(channelId2Client.remove(channelId));
        }
        return Optional.empty();
    }

    public Optional<ClientInstance> getClientId(SocketChannel socketChannel) {
        return Optional.ofNullable(channelId2Client.get(ChannelUtils.getChannelId(socketChannel)));
    }

    public SocketChannel getChannel(Long userId, Integer client) {
        return clients.get(clientId(userId, client));
    }

    private String clientId(Long userId, Integer client) {
        return userId + ":" + client;
    }

    public boolean isConnected(String clientId) {
        return clients.containsKey(clientId);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class ClientInstance {
        private final Long userId;
        private final Integer client;

        public String clientId() {
            return userId + ":" + client;
        }
    }
}
