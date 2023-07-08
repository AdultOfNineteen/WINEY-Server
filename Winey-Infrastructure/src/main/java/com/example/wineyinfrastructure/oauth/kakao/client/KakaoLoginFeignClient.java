package com.example.wineyinfrastructure.oauth.kakao.client;

import com.example.wineyinfrastructure.oauth.kakao.config.KakaoFeignConfiguration;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoLoginTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "kakaoLoginFeignClient",
        url = "https://kauth.kakao.com",
        configuration = KakaoFeignConfiguration.class)
@Component
//Login 코드를 가지고 Token 을 요청하는 Feign
public interface KakaoLoginFeignClient {

    @PostMapping(
            "/oauth/token?grant_type=authorization_code")
    KakaoLoginTokenRes kakaoAuth(
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("client_secret") String client_secret,
            @RequestParam("code") String code
    );
}