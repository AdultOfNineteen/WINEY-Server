package com.example.wineyapi.user.converter;

import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineydomain.user.entity.Authority;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private static PasswordEncoder staticPasswordEncoder;

    @PostConstruct
    void init() {
        this.staticPasswordEncoder = passwordEncoder;
    }

    public static Authority userAuthority() {
        return Authority.builder()
                .authorityName("ROLE_USER")
                .build();
    }

    public static UserResponse.LoginUserDTO toLoginUserDTO(User user, String accessToken, String refreshToken) {
        return UserResponse.LoginUserDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static User toUser(KakaoUserInfoDto kakaoUserInfoDto) {
        return User.builder()
                .profileImgUrl(kakaoUserInfoDto.getProfileUrl())
                .password(staticPasswordEncoder.encode(kakaoUserInfoDto.getId()))
                .socialId(kakaoUserInfoDto.getId())
                .nickName(kakaoUserInfoDto.getName())
                .username(kakaoUserInfoDto.getName())
                .authorities(Collections.singleton(userAuthority()))
                .socialType(SocialType.KAKAO)
                .email("swa07016@winey.com")
                .phoneNumber("01012341234")
                .level(1)
                .build();
    }
}
