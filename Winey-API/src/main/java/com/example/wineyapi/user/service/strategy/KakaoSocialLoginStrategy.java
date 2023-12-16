package com.example.wineyapi.user.service.strategy;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoFeignClient;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoSocialLoginStrategy implements SocialLoginStrategy{

    private final UserRepository userRepository;
    private final KakaoFeignClient kakaoFeignClient;

    @Override
    public User login(UserRequest.LoginUserDTO request) {
        String accessTokenWithBearerPrefix = "Bearer " + request.getAccessToken();
        KakaoUserInfoDto kakaoUserInfoDto = kakaoFeignClient.getInfo(accessTokenWithBearerPrefix);
        return userRepository.findBySocialIdAndSocialType(kakaoUserInfoDto.getId(), SocialType.KAKAO)
                .orElseGet(() -> UserConverter.toUser(kakaoUserInfoDto));
    }
}
