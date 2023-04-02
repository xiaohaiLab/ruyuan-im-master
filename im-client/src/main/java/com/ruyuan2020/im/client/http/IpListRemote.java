package com.ruyuan2020.im.client.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.client.tcp.TcpClient;
import com.ruyuan2020.im.client.util.TokenHolder;
import com.ruyuan2020.im.common.core.domain.JsonResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author case
 */
public class IpListRemote extends Remote {

    private final static String URL = PropertiesUtils.getWebGatewayUrl() + "/api/iplist";

    public IpListRemote(TcpClient tcpClient) {
        super(tcpClient);
    }

    public JsonResult<?> get() {
        String token = TokenHolder.getTokenInfo(tcpClient).getToken();
        Map<String, Object> params = new HashMap<>();
        String jsonStr = HttpRequest.get(URL)
                .bearerAuth(token)
                .form(params)
                .execute().body();
        return JSONUtil.toBean(jsonStr, JsonResult.class);
    }
}
