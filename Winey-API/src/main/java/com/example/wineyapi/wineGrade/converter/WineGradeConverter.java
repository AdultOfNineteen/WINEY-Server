package com.example.wineyapi.wineGrade.converter;

import com.example.wineyapi.wineGrade.dto.WineGradeResponse;
import com.example.wineydomain.common.WineGrade;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WineGradeConverter {
    public static WineGradeResponse.GetWineGradeDTO toGetWineGradeDTO(WineGrade currentGrade, WineGrade nextGrade, Integer threeMonthsNoteCount) {
        return WineGradeResponse.GetWineGradeDTO.builder()
                .currentGrade(currentGrade)
                .expectedNextMonthGrade(nextGrade)
                .threeMonthsNoteCount(threeMonthsNoteCount)
                .build();
    }
}
