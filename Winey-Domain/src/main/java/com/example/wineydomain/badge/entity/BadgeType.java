package com.example.wineydomain.badge.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BadgeType {
    SOMMELIER("소믈리에 배지"),
    ACTIVITY("활동 배지");

    private final String description;
}
