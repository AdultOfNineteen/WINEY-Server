package com.example.wineyapi.user.dto;

import com.example.wineydomain.common.model.PreferenceStatus;
import com.example.wineydomain.common.model.Status;
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
        private Status userStatus;
        private VerifyMessageStatus messageStatus;
        private PreferenceStatus preferenceStatus;
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
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime sentAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReIssueToken {
        private String accessToken;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
	public static class UserInfoDTO {
        private Long userId;

        private Status status;
	}
}
