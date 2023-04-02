package com.ruyuan2020.im.client.http;

import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.client.tcp.TcpClient;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;

/**
 * @author case
 */
public class C2cRemote extends Remote {

    private final static String URL = PropertiesUtils.getWebGatewayUrl() + "/api/iplist";

    public C2cRemote(TcpClient tcpClient) {
        super(tcpClient);
    }

    public JsonResult<?> fetch() {
        return ResultHelper.ok();
    }
}
