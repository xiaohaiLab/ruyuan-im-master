package com.ruyuan2020.im.auth.remote;

import com.ruyuan2020.im.auth.util.SpringContextUtils;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@Component
public class RemoteFactory<T> {

    public Remote <T>getRemote(String domain) {
        return SpringContextUtils.getBean(domain + "Remote");
    }
}
