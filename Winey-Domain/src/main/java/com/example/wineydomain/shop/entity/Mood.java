package com.example.wineydomain.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mood {
    VALUE("가성비"),
    SENSITIVE("감성적인"),
    LUXURY("고급진"),
    DINING("다이닝"),
    DATE("데이트"),
    RETRO("레트로"),
    BAKERY("베이커리"),
    BRUNCH("브런치"),
    SNACK("스낵"),
    STEAK("스테이크"),
    STEW("스튜"),
    COZY("아담한"),
    WOMEN_ONLY("여성전용"),
    RESERVATION("예약필수"),
    WINE("와인전문"),
    ITALIAN("이탈리아"),
    JAPANESE("일식"),
    JEON("전"),
    CHICKEN("치킨"),
    CASUAL("캐주얼한"),
    COURSE("코스요리"),
    PASTA("파스타"),
    FRENCH("프랑스"),
    PLATTER("플래터"),
    PIZZA("피자"),
    FINGER_FOOD("핑거푸드"),
    KOREAN("한식"),
    SALAD("샐러드"),
    DRINK_ALONE("혼술");
    private final String value;
}
