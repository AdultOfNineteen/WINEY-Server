package com.example.wineydomain.wine.entity;

import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class WineSummary {
    private Double avgPrice;
    private Integer avgSweetness;
    private Integer avgAcidity;
    private Integer avgBody;
    private Integer avgTannins;

    // 쿼리 결과를 위한 생성자
    public WineSummary(Double avgPrice, Double avgSweetness, Double avgAcidity, Double avgBody, Double avgTannins) {
        this.avgPrice = roundToFirstDecimal(avgPrice);
        this.avgSweetness = roundOrDefault(avgSweetness);
        this.avgAcidity = roundOrDefault(avgAcidity);
        this.avgBody = roundOrDefault(avgBody);
        this.avgTannins = roundOrDefault(avgTannins);
    }

    private Double roundToFirstDecimal(Double value) {
        return Optional.ofNullable(value)
                .map(v -> Math.round(v * 10.0) / 10.0)
                .orElse(0.0);
    }

    private int roundOrDefault(Double value) {
        return Optional.ofNullable(value)
                .map(v -> (int) Math.round(v))
                .orElse(0);
    }
}
