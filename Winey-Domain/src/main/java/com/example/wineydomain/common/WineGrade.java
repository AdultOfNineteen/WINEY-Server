package com.example.wineydomain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineGrade {
    GLASS(0, 2),
    BOTTLE(3, 6),
    OAK(7, 11),
    WINERY(12, 6);

    private final Integer minCount;
    private final Integer maxCount;
}
