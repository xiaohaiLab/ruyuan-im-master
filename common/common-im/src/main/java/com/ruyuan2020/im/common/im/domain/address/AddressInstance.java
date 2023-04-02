package com.ruyuan2020.im.common.im.domain.address;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author case
 */
@Getter
@Setter
public class AddressInstance {

    private String serverId;

    private String ip;

    private int port;

    public AddressInstance(String serverId, String ip, int port) {
        this.serverId = serverId;
        this.ip = ip;
        this.port = port;
    }

    public String getAddress() {
        return ip + ":" + port;
    }

    @Override
    public String toString() {
        return serverId + ":" + getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInstance that = (AddressInstance) o;
        return port == that.port && Objects.equals(serverId, that.serverId) && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, ip, port);
    }
}
