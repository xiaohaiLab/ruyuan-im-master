package com.ruyuan2020.im.common.im.util;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;

/**
 * @author case
 */
public class AddressUtils {

    public static AddressInstance parseAddress(String address) {
        String[] split = address.split(":");
        if (split.length != 3) {
            throw new SystemException("地址解析错误");
        }
        String serverId = split[0];
        String ip = split[1];
        int port = Integer.parseInt(split[2]);
        return new AddressInstance(serverId, ip, port);
    }
}
