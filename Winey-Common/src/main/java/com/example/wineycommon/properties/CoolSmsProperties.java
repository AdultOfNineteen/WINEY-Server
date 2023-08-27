package com.example.wineycommon.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@ConstructorBinding
@Component
@ConfigurationProperties("cool-sms")
public class CoolSmsProperties {
    private String apiKey;
    private String apiSecret;
    private String fromNumber;
    private String domain;
}
