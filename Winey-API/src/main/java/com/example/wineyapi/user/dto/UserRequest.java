package com.example.wineyapi.user.dto;

import lombok.Getter;

public class UserRequest {

    @Getter
    public static class LoginUserDTO {
        private String accessToken;
    }

    @Getter
    public static class SendCodeDTO {
        private String phoneNumber;
    }

    @Getter
    public static class VerifyCodeDTO {
        private String verificationCode;
    }
}
