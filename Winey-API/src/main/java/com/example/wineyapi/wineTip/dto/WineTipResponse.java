package com.example.wineyapi.wineTip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WineTipResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineTipDto {
        private Long wineTipId;

        private String thumbNail;

        private String title;

        private String url;
    }

}
