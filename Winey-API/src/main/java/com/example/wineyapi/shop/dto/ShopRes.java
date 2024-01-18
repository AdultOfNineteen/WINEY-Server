package com.example.wineyapi.shop.dto;

import com.example.wineydomain.shop.entity.ShopType;
import lombok.*;

import java.math.BigInteger;
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

        private double latitude;

        private double longitude;

        private String businessHour;

        private String imgUrl;

        private String address;

        private String phone;

        private String name;

        private double meter;

        private String shopType;

        private List<String> shopMoods;
    }

    @Getter
    @NoArgsConstructor
    @Setter
    @AllArgsConstructor
    @Builder
    public static class BookmarkDto{
        private boolean isLike;

        private String message;
    }
}
