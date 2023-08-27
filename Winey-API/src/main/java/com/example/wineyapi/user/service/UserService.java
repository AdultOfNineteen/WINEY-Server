package com.example.wineyapi.user.service;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.verificationMessage.entity.VerificationMessage;

public interface UserService {
    User login(SocialType socialType, UserRequest.LoginUserDTO request);

    String getKakaoAccessToken(String code);

    Long delete(Long id);


    VerificationMessage sendCode(Long userId, UserRequest.SendCodeDTO request);

    VerificationMessage verifyCode(Long userId, UserRequest.VerifyCodeDTO request);
}
