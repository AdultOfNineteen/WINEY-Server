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

        @Schema(description = "품종")
        private String varietal;
    }
}
