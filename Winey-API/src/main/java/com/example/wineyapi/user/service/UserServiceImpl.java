package com.example.wineyapi.user.service;

import com.example.wineyapi.user.dto.UserRequest;
import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User login(SocialType socialType, UserRequest.LoginUserDTO request) {
        User user = null;
        switch (socialType) {
            case KAKAO -> user = loginWithKakao(request);
            case GOOGLE -> user = loginWithGoogle(request);
            case APPLE -> user = loginWithApple(request);
        }
        return user;
    }

    private User loginWithKakao(UserRequest.LoginUserDTO request) {
        return null;
    }

    private User loginWithGoogle(UserRequest.LoginUserDTO request) {
        return null;
    }

    private User loginWithApple(UserRequest.LoginUserDTO request) {
        return null;
    }
}
