package com.example.wineyapi.user.service.context;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineyapi.user.service.strategy.SocialLoginStrategy;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SocialLoginContext {
    private final SocialLoginStrategy socialLoginStrategy;

    public User login(UserRequest.LoginUserDTO request) {
        return socialLoginStrategy.login(request);
    }
}
