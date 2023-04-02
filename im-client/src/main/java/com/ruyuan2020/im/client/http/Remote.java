package com.ruyuan2020.im.client.http;

import com.ruyuan2020.im.client.tcp.TcpClient;

/**
 * @author case
 */
abstract class Remote {

    protected TcpClient tcpClient;

    public Remote(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }
}
