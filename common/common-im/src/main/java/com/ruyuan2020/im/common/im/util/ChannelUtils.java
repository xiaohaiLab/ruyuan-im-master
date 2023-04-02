package com.ruyuan2020.im.common.im.util;

import io.netty.channel.socket.SocketChannel;

public class ChannelUtils {

    public static String getChannelId(SocketChannel socketChannel) {
        return socketChannel.id().asLongText();
    }
}
