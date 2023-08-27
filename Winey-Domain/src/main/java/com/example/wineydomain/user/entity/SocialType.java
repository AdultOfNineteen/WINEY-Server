package com.example.wineydomain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {
    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple"),
    normal("normal");

    private final String value;

    }
