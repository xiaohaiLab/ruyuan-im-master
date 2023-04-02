package com.ruyuan2020.im.common.hbase.boot;

import com.ruyuan2020.im.common.hbase.HBaseTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author case
 */
@Configuration
@EnableConfigurationProperties(HBaseProperties.class)
@ConditionalOnClass(HBaseTemplate.class)
@RequiredArgsConstructor
public class HBaseAutoConfiguration {

    private final HBaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HBaseTemplate.class)
    public HBaseTemplate hbaseTemplate() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", this.hbaseProperties.getQuorum());
        configuration.set("hbase.htable.threads.max", this.hbaseProperties.getHtable().getThreadsMax());
        configuration.set("hbase.htable.threads.coresize", this.hbaseProperties.getHtable().getThreadsCoreSize());
        configuration.set("hbase.htable.threads.keepalivetime", this.hbaseProperties.getHtable().getThreadsKeepAliveTime());
        if (StringUtils.isNotBlank(hbaseProperties.getRootDir())) {
            configuration.set("hbase.rootdir", hbaseProperties.getRootDir());
        }
        if (StringUtils.isNotBlank(hbaseProperties.getNodeParent())) {
            configuration.set("zookeeper.znode.parent", hbaseProperties.getNodeParent());
        }
        return new HBaseTemplate(configuration);
    }

}
