package com.example.wineyinfrastructure.oauth.kakao.client;

import com.example.wineyinfrastructure.oauth.kakao.config.KakaoInfoConfig;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoInfoClient",
        url = "https://kapi.kakao.com",
        configuration = KakaoInfoConfig.class)
@Component
//Token을 가지고 회원정보를 요청하는 Feign이다.
public interface KakaoFeignClient {
    @GetMapping("/v2/user/me")
    KakaoUserInfoDto getInfo(@RequestHeader(name = "Authorization") String Authorization);
}
