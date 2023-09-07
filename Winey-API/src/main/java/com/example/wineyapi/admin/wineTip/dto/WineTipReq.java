package com.example.wineyapi.admin.wineTip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class WineTipReq {
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class WineTipDto {
        @Schema(description ="blogUrl", required = true,example = "https://imenu.tistory.com/3")
        private String blogUrl;

        @Schema(description ="제목", required = true,example = "와인 입문자를 위한 TIP")
        private String title;

        @Schema(description ="이미지 파일", required = true,example = "이미지 파일")
        private MultipartFile multipartFile;
    }
}
