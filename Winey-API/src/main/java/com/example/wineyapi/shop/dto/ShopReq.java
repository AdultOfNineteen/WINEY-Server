package com.example.wineyapi.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

public class ShopReq {

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MapFilterDto{
        @NotNull(message = "위도를 입력해주세요")
        @Schema(description = "사용자 위도", example = "37.510845")
        private double latitude;

        @Schema(description = "사용자 경도", example = "127.081854")
        @NotNull(message = "경도를 입력해주세요")
        private double longitude;

        @NotNull(message = "좌측 상단 위도를 입력해주세요")
        @Schema(description = "좌측 상단 위도", example = "37.8085577")
        private double leftTopLatitude;

        @Schema(description = "좌층 상단 경도", example = "126.8960541")
        @NotNull(message = "좌측 상단 경도를 입력해주세요")
        private double leftTopLongitude;

        @NotNull(message = "우측 하단 위도를 입력해주세요")
        @Schema(description = "우측 하단 위도", example = "37.4340242")
        private double rightBottomLatitude;

        @Schema(description = "우측 하단 경도", example = "127.2157401")
        @NotNull(message = "우측 하단 경도를 입력해주세요")
        private double rightBottomLongitude;
    }
}
