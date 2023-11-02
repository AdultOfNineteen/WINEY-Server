package com.example.wineyapi.user.controller;

import com.example.wineyapi.common.annotation.CheckIdExistence;
import com.example.wineyapi.common.annotation.CheckOwnAccount;
import com.example.wineyapi.security.JwtService;
import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.exception.BadRequestException;
import com.example.wineycommon.exception.errorcode.OtherServerErrorCode;
import com.example.wineyapi.user.service.UserService;
import com.example.wineycommon.exception.errorcode.RequestErrorCode;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.redis.entity.RefreshToken;
import com.example.wineydomain.redis.repository.RefreshTokenRepository;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.wineycommon.exception.errorcode.CommonResponseStatus.INVALID_REFRESH_TOKEN;

@Tag(name = "01-User\uD83D\uDC64",description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Operation(summary = "01-01 User\uD83D\uDC64 소셜 로그인 #000_회원가입&로그인", description = "KAKAO, GOOGLE, APPLE 소셜로그인 API입니다.")
    @ApiErrorCodeExample(OtherServerErrorCode.class)
    @PostMapping("/login/{socialType}")
    public CommonResponse<UserResponse.LoginUserDTO> login(@PathVariable SocialType socialType,
                                                           @RequestBody UserRequest.LoginUserDTO request) throws ConversionFailedException {

        User user = userService.login(socialType, request);
        String accessToken = jwtService.createToken(user.getId());
        String refreshToken = jwtService.createRefreshToken(user.getId());

        return CommonResponse.onSuccess(UserConverter.toLoginUserDTO(user, accessToken, refreshToken));
    }

    /**
     * 인가 코드를 바탕으로 카카오 서버에 요청하여 AccessToken을 받는 테스트용 API
     */
    @Deprecated
    @GetMapping("/login/kakao")
    public CommonResponse<String> getAccessTokenKakao(@RequestParam String code) {
        String accessToken = userService.getKakaoAccessToken(code);
        return CommonResponse.onSuccess(accessToken);
    }

    /**
     * KAKAO 서버로부터 인가 코드를 받는 테스트용 API
     */
    @Deprecated
    @GetMapping("/auth/kakao")
    public CommonResponse<String> getAuthorizationCodeKakao(@RequestParam String code) {
        String message = "Go to " + "/login/kakao?code=" + code;
        return CommonResponse.onSuccess(message);
    }


    @Operation(summary = "01-02 User\uD83D\uDC64 회원 탈퇴 #FRAME", description = "회원 탈퇴 API입니다.")
    @DeleteMapping("/users/{userId}")
    @CheckIdExistence @CheckOwnAccount
    public CommonResponse<UserResponse.DeleteUserDTO> deleteUser(@PathVariable Long userId,
                                                                 @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        Long deletedUserId = userService.delete(userId);
        return CommonResponse.onSuccess(UserConverter.toDeleteUserDTO(deletedUserId));
    }

    /**
     * 전화번호를 받아 인증코드를 전송하거나 가입을 중단하는 API
     */
    @Operation(summary = "01-03 User\uD83D\uDC64 인증번호 전송 #000_01_번호 입력 완료", description = "사용자 휴대전화 메시지로 인증번호를 전송하는 API입니다.")
    @PostMapping("/users/{userId}/phone/code/send")
//    @CheckIdExistence
    public CommonResponse<UserResponse.SendCodeDTO> sendCode(@PathVariable Long userId,
                                                             @RequestBody UserRequest.SendCodeDTO request) {
        VerificationMessage sentVerificationMessage = userService.sendCode(userId, request);
        return CommonResponse.onSuccess(UserConverter.toSendCodeDTO(sentVerificationMessage));
    }

    /**
     * 인증코드를 검사하는 API
     */
    @Operation(summary = "01-04 User\uD83D\uDC64 인증번호 검사 #000_02_인증번호 입력", description = "전송받은 인증번호를 확인하는 API입니다.")
    @PostMapping("/users/{userId}/phone/code/verify")
    @CheckIdExistence
    public CommonResponse<UserResponse.VerifyCodeDTO> verifyCode(@PathVariable Long userId,
                                                                 @RequestBody UserRequest.VerifyCodeDTO request) {
        VerificationMessage updatedVerificationMessage = userService.verifyCode(userId, request);
        return CommonResponse.onSuccess(UserConverter.toVerifyCodeDTO(updatedVerificationMessage));
    }

    @Operation(summary = "01-07 User👤 유저 로그아웃 Made By Austin", description = "로그아웃 API 입니다.")
    @GetMapping("/users/logout")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<String> logOut(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @RequestParam String deviceId){
        Long userId = user.getId();
        jwtService.logOut(userId);
        userService.deleteFcmToken(user, deviceId);
        return CommonResponse.onSuccess("로그아웃 성공");
    }

    @Operation(summary = "01-08 User👤 토큰 재발급 Made By Austin", description = "액세스 토큰 만료시 재발급 요청 하는 API X-REFRESH-TOKEN 을 헤더에 담아서 보내주세요, accessToken 은 보내지 않습니다.")
    @PostMapping("/refresh")
    public CommonResponse<UserResponse.ReIssueToken> reIssueToken(
            @Parameter(description = "리프레쉬 토큰", required = true, in = ParameterIn.HEADER, name = "X-REFRESH-TOKEN", schema = @Schema(type = "string")) @RequestHeader("X-REFRESH-TOKEN") String refreshToken
    ){
        Long userId=jwtService.getUserIdByRefreshToken(refreshToken);
        RefreshToken redisRefreshToken= refreshTokenRepository.findById(String.valueOf(userId)).orElseThrow(()-> new BadRequestException(INVALID_REFRESH_TOKEN));

        if(!redisRefreshToken.getToken().equals(refreshToken)) throw new BadRequestException(INVALID_REFRESH_TOKEN);

        UserResponse.ReIssueToken tokenRes=new UserResponse.ReIssueToken(jwtService.createToken(userId));

        return CommonResponse.onSuccess(tokenRes);
    }


    @Operation(summary = "01-09 User👤 유저 스플레쉬 화면 단 호출 부탁합니다.", description = "유저 뱃지 발급을 위한 접속 API 입니다. 토큰만 들고오면 됩니다.")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @GetMapping("/connections")
    public CommonResponse<String> connectionUser(
            @AuthenticationPrincipal User user
    ){
        userService.connectionUser(user);
        return CommonResponse.onSuccess("확인 되었습니다.");
    }

    @Operation(summary = "01-10 User 👤 유저 FCM 토큰 저장", description = "유저 FCM 토큰 전송용 API 입니다.")
    @ApiErrorCodeExample({UserAuthErrorCode.class, RequestErrorCode.class})
    @PostMapping("/fcm")
    public CommonResponse<String> postUserFcmToken(
            @RequestBody UserRequest.UserFcmTokenDto userFcmTokenDto,
            @AuthenticationPrincipal User user
    ){
        userService.postUserFcmToken(userFcmTokenDto, user);

        return CommonResponse.onSuccess("유저 FCM 토큰 저장 완료");
    }

}
