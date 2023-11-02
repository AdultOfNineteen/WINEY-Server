package com.example.wineydomain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    BADGE("BADGE", "뱃지");
    private final String value;

    private final String type;
}
