package com.example.wineyapi.user.service.strategy;

import static com.example.wineydomain.common.model.Status.*;

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
        return userRepository.findBySocialIdAndSocialTypeAndStatus(kakaoUserInfoDto.getId(), SocialType.KAKAO, ACTIVE)
                .orElseGet(() -> UserConverter.toUser(kakaoUserInfoDto));
    }
}
