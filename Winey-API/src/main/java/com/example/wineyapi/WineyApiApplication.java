package com.example.wineyapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.util.Arrays;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = {"com.example.wineyapi","com.example.wineydomain","com.example.wineyinfrastructure", "com.example.wineycommon"})
@OpenAPIDefinition(servers = {@Server(url = "${server.servlet.context-path}", description = "Default Server URL")})
@RequiredArgsConstructor
@EnableCaching
@Slf4j
public class WineyApiApplication implements ApplicationListener<ApplicationReadyEvent> {
    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(WineyApiApplication.class, args);
        long heapSize = Runtime.getRuntime().totalMemory();
        log.info("MATCH API Server - HEAP Size(M) : "+ heapSize / (1024*1024) + " MB");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event){
        log.info("applicationReady status Active Profiles =" + Arrays.toString(environment.getActiveProfiles()));
    }

}
