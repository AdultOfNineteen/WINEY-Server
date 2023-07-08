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
@ConfigurationProperties("jwt")
public class JwtProperties {
    private String header;
    private String secret;
    private String refresh;
    private Long accessTokenSeconds;
    private Long refreshTokenSeconds;


}
