package com.example.wineyapi.user.converter;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineyapi.user.service.UserService;
import com.example.wineydomain.common.model.PreferenceStatus;
import com.example.wineydomain.common.model.Status;
import com.example.wineydomain.common.model.VerifyMessageStatus;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserFcmToken;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import com.example.wineyinfrastructure.oauth.apple.dto.AppleMember;
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
                .isTastingNoteAnalyzed(false)
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
                .isTastingNoteAnalyzed(false)
                .build();
    }

    public static User toUser(AppleMember appleMember) {
        String nickName = staticNickNameFeignClient.getNickName().getWords().get(0);

        /*
            NOTE
            Apple의 Sign in with Apple 기능을 사용할 때 Apple은 사용자의 프로필 이미지를 제공하지 않습니다.
            Apple의 OAuth2 인증을 통해 제공되는 주된 정보는 사용자의 고유한 식별자(Subject), 이메일, 그리고 이름입니다.

            Apple의 정책은 사용자의 개인정보 보호에 중점을 둡니다. 이에 따라, Apple은 기본적으로 사용자의 최소한의 정보만을 제공합니다.
            실제로, 사용자가 Apple ID로 로그인할 때마다 Apple은 새로운, 서비스마다 다른 고유 식별자를 생성하여 전달하며,
            심지어 사용자의 이메일 주소조차도 숨길 수 있는 기능을 제공합니다. 이런 방식은 사용자의 프라이버시를 보호하지만,
            다른 서비스들과는 달리 프로필 이미지 같은 추가 정보를 가져오기 어렵게 만듭니다.

            따라서, 프로필 이미지를 제공하려면 사용자에게 직접 이미지를 업로드하도록 요청하거나 다른 소셜 로그인 서비스를 통해 이미지를 가져와야 합니다.
         */
        return User.builder()
                .profileImgUrl(null)
                .password(staticPasswordEncoder.encode(appleMember.getSocialId()))
                .socialId(appleMember.getSocialId())
                .nickName(nickName)
                .username(createUserName(SocialType.GOOGLE, appleMember.getSocialId()))
                .socialType(SocialType.APPLE)
                .level(1)
                .status(Status.INACTIVE)
                .isTastingNoteAnalyzed(false)
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

    public static UserFcmToken toUserFcmToken(UserRequest.UserFcmTokenDto userFcmTokenDto, User user) {
        return UserFcmToken.builder()
                .fcmToken(userFcmTokenDto.getFcmToken())
                .deviceId(userFcmTokenDto.getDeviceId())
                .user(user)
                .build();
    }
}
