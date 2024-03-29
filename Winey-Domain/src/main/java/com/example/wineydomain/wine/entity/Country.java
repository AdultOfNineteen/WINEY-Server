package com.example.wineydomain.wine.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Getter
@AllArgsConstructor
public enum Country {
    프랑스("프랑스"),
    스페인("스페인"),
    호주("호주"),
    뉴질랜드("뉴질랜드"),
    미국("미국"),
    이탈리아("이탈리아"),
    아르헨티나("아르헨티나"),
    칠레("칠레"),
    조지아("조지아"),
    오스트리아("오스트리아"),
    포르투갈("포르투갈"),
    독일("독일"),
    남아프리카("남아프리카"),
    체코("체코"),
    슬로베니아("슬로베니아"),
    그리스("그리스"),
    우루과이("우루과이"),
    헝가리("헝가리"),
    몰도바("몰도바"),
    남아프리카_공화국("남아프리카 공화국"),
    스웨덴("스웨덴"),
    불가리아("불가리아"),
    캐나다("캐나다"),
    대한민국("대한민국"),
    크로아티아("크로아티아"),
    중국("중국"),
    일본("일본"),
    루마니아("루마니아"),
    아제르바이잔("아제르바이잔"),
    레바논("레바논"),
    이스라엘("이스라엘"),
    덴마크("덴마크"),
    기타국가("기타국가"),
    영국("영국");

    private final String value;

}
