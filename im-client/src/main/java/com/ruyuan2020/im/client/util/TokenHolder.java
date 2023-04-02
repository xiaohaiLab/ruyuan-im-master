package com.ruyuan2020.im.client.util;

import com.ruyuan2020.im.client.tcp.TcpClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author case
 */
public class TokenHolder {

    private final static Map<TcpClient, TokenInfo> tokenInfoMap = new ConcurrentHashMap<>();

    public static TokenInfo getTokenInfo(TcpClient tcpClient) {
        return tokenInfoMap.get(tcpClient);
    }

    public static void setTokenInfo(TcpClient tcpClient, TokenInfo tokenInfo) {
        TokenHolder.tokenInfoMap.put(tcpClient, tokenInfo);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TokenInfo {

        private String token;

        private Long userId;
    }

}
