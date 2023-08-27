package com.example.wineyapi.user.controller;

import com.example.wineyapi.common.annotation.CheckIdExistence;
import com.example.wineyapi.security.JwtService;
import com.example.wineyapi.user.converter.UserConverter;
import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.exception.errorcode.OtherServerErrorCode;
import com.example.wineyapi.user.service.UserService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "유저 소셜 로그인, 휴대폰 인증")
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "소셜 로그인 API", description = "KAKAO, GOOGLE, APPLE 소셜로그인")
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


    @DeleteMapping("/users/{userId}")
    @CheckIdExistence
    public CommonResponse<UserResponse.DeleteUserDTO> deleteUser(@PathVariable Long userId) {
        Long deletedUserId = userService.delete(userId);
        return CommonResponse.onSuccess(UserConverter.toDeleteUserDTO(deletedUserId));
    }

    /**
     * 전화번호를 받아 인증코드를 전송하거나 가입을 중단하는 API
     */
    @Operation(summary = "인증 번호 전송 API", description = "사용자 휴대전화 메시지로 인증 번호 전송")
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
    @Operation(summary = "인증 번호 검사 API", description = "전송 받은 인증번호 확인")
    @PostMapping("/users/{userId}/phone/code/verify")
    @CheckIdExistence
    public CommonResponse<UserResponse.VerifyCodeDTO> verifyCode(@PathVariable Long userId,
                                                                 @RequestBody UserRequest.VerifyCodeDTO request) {
        VerificationMessage updatedVerificationMessage = userService.verifyCode(userId, request);
        return CommonResponse.onSuccess(UserConverter.toVerifyCodeDTO(updatedVerificationMessage));
    }
}
