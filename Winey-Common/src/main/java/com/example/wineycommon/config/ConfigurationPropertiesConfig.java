package com.example.wineycommon.config;

import com.example.wineycommon.properties.JwtProperties;
import com.example.wineycommon.properties.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties({
        KakaoProperties.class,
        JwtProperties.class,
        RedisConfig.class
})
@Configuration
public class ConfigurationPropertiesConfig {}
