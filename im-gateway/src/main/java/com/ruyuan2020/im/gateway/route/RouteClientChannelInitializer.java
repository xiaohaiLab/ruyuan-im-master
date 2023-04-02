package com.ruyuan2020.im.gateway.route;

import com.ruyuan2020.im.common.protobuf.Command;
import com.ruyuan2020.im.gateway.util.SpringContextUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Scope("prototype")
@Component
@RequiredArgsConstructor
public class RouteClientChannelInitializer extends ChannelInitializer<Channel> {

    private final ProtobufDecoder protobufDecoder = new ProtobufDecoder(Command.getDefaultInstance());

    private final ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender = new ProtobufVarint32LengthFieldPrepender();

    private final ProtobufEncoder protobufEncoder = new ProtobufEncoder();

    private final RouteClientChannelHandler routeClientChannelHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // protobuf解码器
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(protobufDecoder)
                .addLast(protobufVarint32LengthFieldPrepender)
                .addLast(protobufEncoder)
                .addLast(routeClientChannelHandler);
    }
}
