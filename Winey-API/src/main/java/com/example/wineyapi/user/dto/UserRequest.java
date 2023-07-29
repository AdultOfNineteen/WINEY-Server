package com.example.wineyapi.user.dto;

import lombok.Getter;

public class UserRequest {

    @Getter
    public static class LoginUserDTO {
        private String accessToken;
    }
}
