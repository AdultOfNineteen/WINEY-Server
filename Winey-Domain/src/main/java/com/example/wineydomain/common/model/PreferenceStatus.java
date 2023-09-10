package com.example.wineydomain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PreferenceStatus {
    DONE("설정완료"),
    NONE("기록없음");

    private final String description;
}
