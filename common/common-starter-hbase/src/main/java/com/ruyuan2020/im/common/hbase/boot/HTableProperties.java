package com.ruyuan2020.im.common.hbase.boot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author case
 */
@Getter
@Setter
public class HTableProperties {

    private String threadsMax = "64";

    private String threadsCoreSize = "30";

    private String threadsKeepAliveTime = "5";


}
