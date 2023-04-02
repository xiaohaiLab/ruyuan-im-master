package com.ruyuan2020.im;

import com.ruyuan2020.im.gateway.server.GatewayServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
@RequiredArgsConstructor
public class ImGatewayApplication implements ApplicationListener<ApplicationReadyEvent> {

    private final GatewayServer gatewayServer;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ImGatewayApplication.class);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        gatewayServer.start();
    }
}
