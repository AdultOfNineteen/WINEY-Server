package com.example.wineydomain.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopType {
    PUB("펍"),
    BOTTLE_SHOP("바틀샵"),
    BAR("바"),
    COOKING_BAR("요리 주점"),
    RESTAURANT("음식점"),
    CAFE("카페")
    ;

    private final String type;

}
