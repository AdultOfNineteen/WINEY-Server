package com.example.wineyapi.user.converter;

import com.example.wineyapi.user.dto.UserResponse;
import com.example.wineydomain.user.entity.User;

public class UserConverter {

    public static UserResponse.LoginUserDTO toLoginUserDTO(User user, String accessToken, String refreshToken) {
        return UserResponse.LoginUserDTO.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
