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
        @Schema(description = "사용자 위도", example = "37.5099121")
        private double latitude;

        @Schema(description = "사용자 경도", example = "127.059554")
        @NotNull(message = "경도를 입력해주세요")
        private double longitude;

        @NotNull(message = "좌측 상단 위도를 입력해주세요")
        @Schema(description = "좌측 상단 위도", example = "37.5099121")
        private double leftTopLatitude;

        @Schema(description = "좌층 상단 경도", example = "127.0845284")
        @NotNull(message = "좌측 상단 경도를 입력해주세요")
        private double leftTopLongitude;

        @NotNull(message = "우측 하단 위도를 입력해주세요")
        @Schema(description = "우측 하단 위도", example = "37.5099121")
        private double rightBottomLatitude;

        @Schema(description = "우측 하단 경도", example = "127.0845284")
        @NotNull(message = "우측 하단 경도를 입력해주세요")
        private double rightBottomLongitude;
    }
}
