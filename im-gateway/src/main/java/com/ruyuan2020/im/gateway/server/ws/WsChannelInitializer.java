package com.ruyuan2020.im.gateway.server.ws;

import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class WsChannelInitializer extends ChannelInitializer<Channel> {

    private final ConfigProperties configProperties;

    private final WsServerHandler wsServerHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // 设置心跳检查
                .addLast(new IdleStateHandler(configProperties.getHeartbeat().getReadTimeout() / 1000, 0, 0))
                // 设置web socket编码器
                .addLast(new HttpServerCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(8192))
                .addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10))
                .addLast(wsServerHandler);
    }
}
