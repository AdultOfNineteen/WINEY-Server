package com.example.wineyapi.wine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Map;

public class WineResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineDTO {
        private String field;
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
        private String recommendCountry;

        private String recommendVarietal;

        private String recommendWineType;

        private int totalWineCnt;

        private int buyAgainCnt;

        private int redCnt;

        private int whiteCnt;

        private int sparklingCnt;

        private int roseCnt;

        private int fortifiedCnt;

        private int otherCnt;

        @Schema(description = "key = country, value = percent 식인 List 입니다.")
        private List<Top3Country> top3Country;

        @Schema(description = "key = varietal, value = percent 식인 List 입니다.")
        private List<Top3Varietal> top3Varietal;

        @Schema(description = "key = smell, value = percent 식인 List 입니다. ")
        private List<Top7Smell> top7Smell;


        private Taste taste;

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

}
