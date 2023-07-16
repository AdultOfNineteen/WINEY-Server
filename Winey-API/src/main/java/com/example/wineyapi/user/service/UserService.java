package com.example.wineyapi.user.service;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.User;

public interface UserService {
    User loginKakao(UserRequest.LoginUserDTO request);
}
