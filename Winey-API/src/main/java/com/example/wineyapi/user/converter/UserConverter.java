package com.example.wineyapi.user.converter;

import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.user.entity.Authority;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import com.example.wineyinfrastructure.user.client.NickNameFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final NickNameFeignClient nickNameFeignClient;
    private static PasswordEncoder staticPasswordEncoder;
    private static NickNameFeignClient staticNickNameFeignClient;

    @PostConstruct
    void init() {
        staticPasswordEncoder = this.passwordEncoder;
        staticNickNameFeignClient = this.nickNameFeignClient;
    }

    public static Authority createUserAuthority() {
        return Authority.builder()
                .authorityName("ROLE_USER")
                .build();
    }

    public static String createUserName(SocialType socialType, String socialId) {
        return socialType.name() + "-" + socialId;
    }

    public static UserResponse.LoginUserDTO toLoginUserDTO(User user, String accessToken, String refreshToken) {
        return UserResponse.LoginUserDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static User toUser(KakaoUserInfoDto kakaoUserInfoDto) {

        String nickName = staticNickNameFeignClient.getNickName().getWords().get(0);

        return User.builder()
                .profileImgUrl(kakaoUserInfoDto.getProfileUrl())
                .password(staticPasswordEncoder.encode(kakaoUserInfoDto.getId()))
                .socialId(kakaoUserInfoDto.getId())
                .nickName(nickName)
                .username(createUserName(SocialType.KAKAO, kakaoUserInfoDto.getId()))
                .authorities(Collections.singleton(createUserAuthority()))
                .socialType(SocialType.KAKAO)
                .level(1)
                .status(Status.INACTIVE)
                .build();
    }
}
