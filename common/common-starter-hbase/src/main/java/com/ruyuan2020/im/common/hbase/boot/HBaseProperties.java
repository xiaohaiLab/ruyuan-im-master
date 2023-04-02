package com.ruyuan2020.im.common.hbase.boot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author case
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.hbase")
public class HBaseProperties {

    private String quorum;

    private String rootDir;

    private String nodeParent;

    private HTableProperties htable = new HTableProperties();

}
