package com.example.wineydomain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Chocolate {
    MILK("밀크초콜릿"),
    DARK("다크초콜릿"),
    NONE("응답안함");

    private final String description;
}
