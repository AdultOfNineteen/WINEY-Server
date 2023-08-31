package com.example.wineydomain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Coffee {
    AMERICANO("아메리카노"),
    CAFE_LATTE("카페 라떼"),
    NONE("응답안함");

    private final String description;
}
