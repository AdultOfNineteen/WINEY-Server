package com.example.wineyinfrastructure.oauth.google.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class GoogleOauth2FeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> template.header("Content-Type", "application/json; charset=UTF-8");
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GoogleOauth2ErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
