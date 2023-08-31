package com.example.wineydomain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Fruit {
    PEACH("복숭아, 자두, 망고"),
    PINEAPPLE("파인애플, 수박, 멜론"),
    NONE("응답안함");

    private final String description;
}
