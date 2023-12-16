package com.example.wineyapi.user.service.strategy;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.User;

public interface SocialLoginStrategy {
    User login(UserRequest.LoginUserDTO request);
}
