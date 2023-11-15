package com.example.wineyapi.shop.dto;

import lombok.*;

public class ShopRes {

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ShopMapDto {
        private Long shopId;

        private boolean isLike;

        private String name;

        private double meter;
    }
}
