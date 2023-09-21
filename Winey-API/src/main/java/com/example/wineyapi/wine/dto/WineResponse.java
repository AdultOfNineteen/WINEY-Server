package com.example.wineyapi.wine.dto;

import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineSummary;
import com.example.wineydomain.wine.entity.WineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

public class WineResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineDTO {
        private Long wineId;
        private String type;
        private String name;
        private String country;
        private String varietal;

        private Integer sweetness;
        private Integer acidity;
        private Integer body;
        private Integer tannins;

        private WineSummary wineSummary;
    }


    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineListDTO {
        private List<WineDTO> wineList;
        private Integer size;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateWineDTO {
        private String field;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class RecommendWineDTO {
        private Long wineId;

        private String name;

        private String country;

        private String type;

        private List<String> varietal;

        private int price;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class TasteAnalysisDTO {
        @Schema(name = "추천 국가")
        private String recommendCountry;

        @Schema(name = "추천 품종")
        private String recommendVarietal;

        @Schema(name = "추천 와인타입")
        private String recommendWineType;

        @Schema(name = "총 마신 와인 병수")
        private int totalWineCnt;

        @Schema(name = "재구매 의사가 있는 와인")
        private int buyAgainCnt;

        @Schema(name = "레드 와인 갯수")
        private int redCnt;

        @Schema(name = "화이트 와인 갯수")
        private int whiteCnt;

        @Schema(name = "스파클링 와인 갯수")
        private int sparklingCnt;

        @Schema(name = "로제와인 갯수")
        private int roseCnt;

        @Schema(name = "주정강화 갯수")
        private int fortifiedCnt;

        @Schema(name = "기타 와인 갯수")
        private int otherCnt;

        @Schema(description = "key = country, value = percent 식인 List 입니다.")
        private List<Top3Country> top3Country;

        @Schema(description = "key = varietal, value = percent 식인 List 입니다.")
        private List<Top3Varietal> top3Varietal;

        @Schema(description = "key = smell, value = percent 식인 List 입니다. ")
        private List<Top7Smell> top7Smell;

        private Taste taste;

        @Schema(name = "평균 와인 가격")
        private int avgPrice;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Taste{
        private double sweetness;

        private double acidity;

        private double alcohol;

        private double body;

        private double tannin;

        private double finish;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top3Country{
        private String country;

        private int percent;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top3Varietal{
        private String varietal;

        private int percent;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top7Smell{
        private String smell;

        private int percent;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SearchWineDto {
        @Schema(name = "와인 id")
        private Long wineId;

        @Schema(name = "와인 타입 RED, WHITE 등등")
        private WineType type;

        @Schema(name = "와인 생산 국가")
        private Country country;

        @Schema(name = "와인 이름")
        private String name;
    }
}
