package com.example.wineyapi.user.dto;

import com.example.wineydomain.common.model.VerifyMessageStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class LoginUserDTO {
        private Long userId;
        private String accessToken;
        private String refreshToken;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class DeleteUserDTO {
        private Long userId;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime deletedAt;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class SendCodeDTO {
        private String phoneNumber;
        private LocalDateTime sentAt;
        private LocalDateTime expireAt;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class VerifyCodeDTO {
        private String phoneNumber;
        private VerifyMessageStatus status;
        private Integer mismatchAttempts;
    }
}
