package com.ruyuan2020.im.route.server;

import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.route.properties.ConfigProperties;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.flow.FlowControlHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Slf4j
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class RouteServerChannelInitializer extends ChannelInitializer<Channel> {

    private final ConfigProperties configProperties;

    private final RouteServerHandler routeServerHandler;

    private final ProtobufDecoder protobufDecoder = new ProtobufDecoder(Command.getDefaultInstance());

    private final ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender = new ProtobufVarint32LengthFieldPrepender();

    private final ProtobufEncoder protobufEncoder = new ProtobufEncoder();

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // 设置心跳检查
                .addLast(new IdleStateHandler(configProperties.getHeartbeat().getReadTimeout() / 1000, 0, 0))
                // protobuf解码器
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(protobufDecoder)
                .addLast(protobufVarint32LengthFieldPrepender)
                .addLast(protobufEncoder)
                .addLast(routeServerHandler);
    }
}
