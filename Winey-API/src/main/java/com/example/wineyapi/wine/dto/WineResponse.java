package com.example.wineyapi.wine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

        private String country;

        private String kind;

        private String type;

        private List<String> varietal;

        private int price;
    }
}
