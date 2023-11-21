package com.example.wineyapi.wineGrade.dto;

import com.example.wineydomain.common.WineGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WineGradeResponse {


    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class GetWineGradeDTO {
        @Schema(description = "사용자의 현재 와인 등급입니다.", example = "GLASS")
        private WineGrade currentGrade;
        @Schema(description = "사용자의 다음 달 예상 와인등급입니다.", example = "BOTTLE")
        private WineGrade expectedNextMonthGrade;
        @Schema(description = "사용자의 직전 3개월 작성 노트 개수입니다.", example = "5")
        private Integer threeMonthsNoteCount;
    }
}
