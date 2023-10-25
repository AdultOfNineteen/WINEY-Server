package com.example.wineycommon.config;

import com.example.wineycommon.properties.JwtProperties;
import com.example.wineycommon.properties.KakaoProperties;
import com.example.wineycommon.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        RedisProperties.class
})
@Configuration
public class ConfigurationPropertiesConfig {}
