package com.example.wineyapi.user.service;

import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineycommon.properties.KakaoProperties;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.repository.UserRepository;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoFeignClient;
import com.example.wineyinfrastructure.oauth.kakao.client.KakaoLoginFeignClient;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KakaoFeignClient kakaoFeignClient;
    private final KakaoLoginFeignClient kakaoLoginFeignClient;
    private final UserRepository userRepository;
    private final KakaoProperties kakaoProperties;

    @Override
    public User login(SocialType socialType, UserRequest.LoginUserDTO request) {
        User user = null;
        switch (socialType) {
            case KAKAO -> user = loginWithKakao(request);
            case GOOGLE -> user = loginWithGoogle(request);
            case APPLE -> user = loginWithApple(request);
        }
        return user;
    }

    @Override
    public String getKakaoAccessToken(String code) {
        return kakaoLoginFeignClient.kakaoAuth(
                kakaoProperties.getKakaoClientId(),
                kakaoProperties.getKakaoRedirectUrl(),
                kakaoProperties.getKakaoClientSecret(),
                code)
                .getAccess_token();
    }

    private User loginWithKakao(UserRequest.LoginUserDTO request) {
        String accessToken = request.getAccessToken();
        KakaoUserInfoDto kakaoUserInfoDto = kakaoFeignClient.getInfo(accessToken);
        return userRepository.findBySocialIdAndSocialType(kakaoUserInfoDto.getId(), SocialType.KAKAO)
                .orElseGet(() -> createUser(kakaoUserInfoDto));
    }

    private User createUser(KakaoUserInfoDto kakaoUserInfoDto) {
        User user = UserConverter.toUser(kakaoUserInfoDto);
        return userRepository.save(user);
    }

    private User loginWithGoogle(UserRequest.LoginUserDTO request) {
        return null;
    }

    private User loginWithApple(UserRequest.LoginUserDTO request) {
        return null;
    }
}
