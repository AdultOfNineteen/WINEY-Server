package com.example.wineyapi.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class UserRequest {

    @Getter
    public static class LoginUserDTO {
        @Schema(description = "ex. KAKAO에서 받은 액세스 토큰" , example = "VRapwU1DESBDiUVPyA4oGKIfi-vJPkuVNGc2HXoICiolkAAAAYonQTRy")
        private String accessToken;
    }

    @Getter
    public static class SendCodeDTO {
        private String phoneNumber;
    }

    @Getter
    public static class VerifyCodeDTO {
        private String phoneNumber;
        private String verificationCode;
    }

    @Getter
    public static class UserFcmTokenDto {
        private String fcmToken;

        private String deviceId;
    }
}
