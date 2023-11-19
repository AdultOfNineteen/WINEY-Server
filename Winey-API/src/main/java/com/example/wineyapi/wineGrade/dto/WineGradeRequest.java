package com.example.wineyapi.wineGrade.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WineGradeRequest {

    @Getter
    public static class GetWineGradeDTO {
        private String field;
    }
}
