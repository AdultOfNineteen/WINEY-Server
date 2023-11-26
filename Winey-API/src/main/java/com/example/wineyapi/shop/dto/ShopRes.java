package com.example.wineyapi.shop.dto;

import com.example.wineydomain.shop.entity.ShopType;
import lombok.*;

import java.util.List;

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

        private List<ShopType> shopTypes;
    }
}
