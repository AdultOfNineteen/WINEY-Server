package com.example.wineyapi.user.dto;

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
}
