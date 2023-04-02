package com.ruyuan2020.im.client.config;

import ch.qos.logback.classic.*;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;

public class DefaultLogConfig extends BasicConfigurator {

    @Override
    public void configure(LoggerContext lc) {
        addInfo("Setting up default configuration.");

        ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<>();
        ca.setContext(lc);
        ca.setName("console");
        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
        encoder.setContext(lc);


        // same as
        PatternLayout layout = new PatternLayout();
        layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} %L - %msg%n");
//        TTLLLayout layout = new TTLLLayout();

        layout.setContext(lc);
        layout.start();
        encoder.setLayout(layout);

        ca.setEncoder(encoder);
        ca.start();

        Logger rootLogger = lc.getLogger(PropertiesUtils.getLogPackage());
        rootLogger.setLevel(Level.toLevel(PropertiesUtils.getLogLevel()));
        rootLogger.addAppender(ca);
    }
}