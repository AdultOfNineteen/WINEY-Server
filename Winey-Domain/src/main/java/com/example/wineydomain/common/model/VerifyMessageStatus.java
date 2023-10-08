package com.example.wineydomain.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerifyMessageStatus {
    PENDING("인증전"),
    VERIFIED("인증완료"),
    FAILED("인증실패"),
    NONE("기록없음");

    private final String description;
}
