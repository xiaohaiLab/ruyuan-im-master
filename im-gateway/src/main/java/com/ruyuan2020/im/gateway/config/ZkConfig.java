package com.ruyuan2020.im.gateway.config;

import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author case
 */
@Configuration
@RequiredArgsConstructor
public class ZkConfig {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
        cal.set(Calendar.DATE, cal.getMaximum(cal.DATE));
        String format2 = format.format(cal.getTime());
        System.out.println(format2);
    }

    /**
     * 功能: 得到上个月月初
     *
     * @return
     */
    public static String lastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String format2 = format.format(cal.getTime());
        return format2;
    }

    private final ConfigProperties configProperties;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(configProperties.getZk().getIntervalTime(), configProperties.getZk().getRetry());
        CuratorFramework framework = CuratorFrameworkFactory.newClient(configProperties.getZk().getZkServer(), retryPolicy);
        framework.start();
        return framework;
    }

    @Bean
    public ZkClient zkClient(CuratorFramework curatorFramework) {
        return new ZkClient(curatorFramework);
    }
}
