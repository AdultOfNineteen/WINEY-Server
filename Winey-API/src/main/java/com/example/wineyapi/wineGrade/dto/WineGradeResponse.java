package com.example.wineyapi.wineGrade.dto;

import com.example.wineydomain.common.WineGrade;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WineGradeResponse {


    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class GetWineGradeDTO {
        private WineGrade currentGrade;
        private WineGrade nextGrade;
        private Integer needCount;
    }
}
