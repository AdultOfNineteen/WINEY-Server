package com.example.wineyapi.user.controller;

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
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final JwtService jwtService;
  
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
    @GetMapping("/login/kakao")
    public CommonResponse<String> getAccessTokenKakao(@RequestParam String code) {
        String accessToken = userService.getKakaoAccessToken(code);
        return CommonResponse.onSuccess(accessToken);
    }

    /**
     * KAKAO 서버로부터 인가 코드를 받는 테스트용 API
     */
    @GetMapping("/auth/kakao")
    public CommonResponse<String> getAuthorizationCodeKakao(@RequestParam String code) {
        String message = "Go to " + "/login/kakao?code=" + code;
        return CommonResponse.onSuccess(message);
    }


    @DeleteMapping("/users/{userId}")
    public CommonResponse<UserResponse.DeleteUserDTO> deleteUser(@PathVariable Long userId) {
        return null;
    }
}
