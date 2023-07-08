package com.example.wineyinfrastructure.oauth.kakao.dto;

import com.example.wineycommon.properties.KakaoProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoLoginTokenReq {
    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private final String grant_type = "authorization_code";

    public static KakaoLoginTokenReq newInstance(KakaoProperties kakaoInfo, String code){
        return KakaoLoginTokenReq.builder()
                .client_id(kakaoInfo.getKakaoClientId())
                .client_secret(kakaoInfo.getKakaoClientSecret())
                .redirect_uri(kakaoInfo.getKakaoRedirectUrl())
                .code(code)
                .build();
    }

    // kakao는 Content-Type 을 application/x-www-form-urlencoded 로 받는다.
// FeignClient는 기본이 JSON으로 변경하니 아래처럼 데이터를 변환 후 보내야 한다.
    @Override
    public String toString() {
        return
                "code=" + code + '&' +
                        "client_id=" + client_id + '&' +
                        "client_secret=" + client_secret + '&' +
                        "redirect_uri=" + redirect_uri + '&' +
                        "grant_type=" + grant_type;
    }
}
