package com.example.wineyapi.user.service;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;

public interface UserService {
    User login(SocialType socialType, UserRequest.LoginUserDTO request);

    String getKakaoAccessToken(String code);

    Long delete(Long id);


    void sendCode(Long userId, UserRequest.SendCodeDTO request);
}
