package com.ruyuan2020.im.client.tcp;

import com.ruyuan2020.im.client.listener.CommandListener;
import com.ruyuan2020.im.common.protobuf.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author case
 */
public class TcpClientChannelInitializer extends ChannelInitializer<Channel> {

    private final ProtobufDecoder protobufDecoder = new ProtobufDecoder(Command.getDefaultInstance());

    private final ProtobufVarint32LengthFieldPrepender protobufVarint32LengthFieldPrepender = new ProtobufVarint32LengthFieldPrepender();

    private final ProtobufEncoder protobufEncoder = new ProtobufEncoder();

    private final TcpClientChannelHandler tcpClientChannelHandler;

    public TcpClientChannelInitializer(TcpClient tcpClient) {
        this.tcpClientChannelHandler = new TcpClientChannelHandler(tcpClient);
    }

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // protobuf解码器
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(protobufDecoder)
                .addLast(protobufVarint32LengthFieldPrepender)
                .addLast(protobufEncoder)
                .addLast(tcpClientChannelHandler);
    }
}
