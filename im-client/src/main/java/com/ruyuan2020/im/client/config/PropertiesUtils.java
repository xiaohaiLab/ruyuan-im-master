package com.ruyuan2020.im.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

    private static final String WEB_GATEWAY_URL = "web.gateway.url";

    private static final String ID_SERVER_URL = "id.server.url";

    private static final String ID_CACHE_COUNT = "id.cache.count";

    private static final String HEARTBEAT_INTERVAL = "heartbeat.interval";

    private static final String CONNECT_MAX_RETRY = "connect.max.retry";

    private static final String COMMAND_MAX_RETRY = "command.max.retry";

    private static final String MESSAGE_ACK_WAIT_TIME = "message.ack.waitTime";

    private static final String IM_GATEWAY_ADDRESS = "im.gateway.address";

    private static final String LOG_LEVEL = "logging.level";

    private static final String LOG_PACKAGE = "logging.package";

    private static final String PROP_PATH = "/config.properties";

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            File file = new File("conf" + PROP_PATH);
            if (file.exists()) {
                prop.load(new FileInputStream(file));
            } else {
                InputStream in = PropertiesUtils.class.getResourceAsStream(PROP_PATH);
                prop.load(in);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static String getWebGatewayUrl() {
        return prop.getProperty(WEB_GATEWAY_URL);
    }

    public static String getIdServerUrl() {
        return prop.getProperty(ID_SERVER_URL);
    }

    public static int getIdCacheCount() {
        return Integer.parseInt(prop.getProperty(ID_CACHE_COUNT));
    }

    public static int getHeartbeatInterval() {
        return Integer.parseInt(prop.getProperty(HEARTBEAT_INTERVAL));
    }

    public static int getConnectMaxRetry() {
        return Integer.parseInt(prop.getProperty(CONNECT_MAX_RETRY));
    }

    public static int getCommandMaxRetry() {
        return Integer.parseInt(prop.getProperty(COMMAND_MAX_RETRY));
    }

    public static int getMessageAckWaitTime() {
        return Integer.parseInt(prop.getProperty(MESSAGE_ACK_WAIT_TIME));
    }

    public static String getImGatewayAddress() {
        return prop.getProperty(IM_GATEWAY_ADDRESS);
    }

    public static String getLogLevel() {
        return prop.getProperty(LOG_LEVEL, "info");
    }

    public static String getLogPackage() {
        return prop.getProperty(LOG_PACKAGE, "com.ruyuan2020.im.client");
    }

}

