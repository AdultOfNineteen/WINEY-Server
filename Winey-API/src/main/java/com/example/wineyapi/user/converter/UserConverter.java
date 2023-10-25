package com.example.wineyapi.user.converter;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineyapi.user.service.UserService;
import com.example.wineydomain.common.model.PreferenceStatus;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.common.model.VerifyMessageStatus;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import com.example.wineyinfrastructure.oauth.google.dto.GoogleUserInfo;
import com.example.wineyinfrastructure.oauth.kakao.dto.KakaoUserInfoDto;
import com.example.wineyinfrastructure.user.client.NickNameFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final NickNameFeignClient nickNameFeignClient;
    private final UserService userService;

    private static PasswordEncoder staticPasswordEncoder;
    private static NickNameFeignClient staticNickNameFeignClient;
    private static UserService staticUserService;

    @PostConstruct
    void init() {
        staticPasswordEncoder = this.passwordEncoder;
        staticNickNameFeignClient = this.nickNameFeignClient;
        staticUserService = this.userService;
    }

    public static String createUserName(SocialType socialType, String socialId) {
        return socialType.name() + "-" + socialId;
    }

    public static UserResponse.LoginUserDTO toLoginUserDTO(User user, String accessToken, String refreshToken) {
        VerifyMessageStatus verifyMessageStatus = staticUserService.findVerifyMessageStatusByUser(user);
        PreferenceStatus preferenceStatus =
                Optional.ofNullable(user.getPreference())
                        .map(preference -> PreferenceStatus.DONE)
                        .orElse(PreferenceStatus.NONE);

        return UserResponse.LoginUserDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userStatus(user.getStatus())
                .messageStatus(verifyMessageStatus)
                .preferenceStatus(preferenceStatus)
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
                .socialType(SocialType.KAKAO)
                .level(1)
                .status(Status.INACTIVE)
                .build();
    }

    public static User toUser(GoogleUserInfo googleUserInfo) {
        String nickName = staticNickNameFeignClient.getNickName().getWords().get(0);

        return User.builder()
                .profileImgUrl(googleUserInfo.getPicture())
                .password(staticPasswordEncoder.encode(googleUserInfo.getSub()))
                .socialId(googleUserInfo.getSub())
                .nickName(nickName)
                .username(createUserName(SocialType.GOOGLE, googleUserInfo.getSub()))
                .socialType(SocialType.GOOGLE)
                .level(1)
                .status(Status.INACTIVE)
                .build();
    }

    public static UserResponse.DeleteUserDTO toDeleteUserDTO(Long deletedUserId) {
        return UserResponse.DeleteUserDTO.builder()
                .userId(deletedUserId)
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static UserResponse.SendCodeDTO toSendCodeDTO(VerificationMessage verificationMessage) {
        return UserResponse.SendCodeDTO.builder()
                .phoneNumber(verificationMessage.getPhoneNumber())
                .sentAt(verificationMessage.getCreatedAt())
                .expireAt(verificationMessage.getExpireAt())
                .build();
    }

    public static UserResponse.VerifyCodeDTO toVerifyCodeDTO(VerificationMessage verificationMessage) {
        return UserResponse.VerifyCodeDTO.builder()
                .phoneNumber(verificationMessage.getPhoneNumber())
                .status(verificationMessage.getStatus())
                .mismatchAttempts(verificationMessage.getMismatchAttempts())
                .build();
    }

    public static VerificationMessage toVerificationMessage(UserRequest.SendCodeDTO request, String verificationNumber) {
        return VerificationMessage.builder()
                .status(VerifyMessageStatus.PENDING)
                .verificationNumber(verificationNumber)
                .requestedAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now().plusMinutes(5))
                .phoneNumber(request.getPhoneNumber())
                .mismatchAttempts(0)
                .build();
    }
}
