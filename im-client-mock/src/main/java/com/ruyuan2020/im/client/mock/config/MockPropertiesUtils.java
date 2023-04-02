package com.ruyuan2020.im.client.mock.config;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class MockPropertiesUtils {

    private static final String CONNECT_THREAD_COUNT = "connect.thread.count";

    private static final String CONNECT_LOOP_COUNT = "connect.loop.count";

    private static final String DEFAULT_GROUP_ID = "default.groupId";

    private static final String MOCK = "mock";

    private static final String PROP_PATH = "/mock.properties";

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            File file = new File("conf" + PROP_PATH);
            if (file.exists()) {
                prop.load(new FileInputStream(file));
            } else {
                InputStream in = MockPropertiesUtils.class.getResourceAsStream(PROP_PATH);
                prop.load(in);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static long getConnectThreadCount() {
        return Long.parseLong(prop.getProperty(CONNECT_THREAD_COUNT, "1"));
    }

    public static long getConnectLoopCount() {
        return Long.parseLong(prop.getProperty(CONNECT_LOOP_COUNT, "1"));
    }

    public static long getDefaultGroupId() {
        return Long.parseLong(prop.getProperty(DEFAULT_GROUP_ID));
    }

    public static long getMockNo() {
        return Long.parseLong(System.getProperty("mock.no", "0"));
    }

    public static boolean isMock() {
        return Boolean.parseBoolean(prop.getProperty(MOCK, "false"));
    }
}

