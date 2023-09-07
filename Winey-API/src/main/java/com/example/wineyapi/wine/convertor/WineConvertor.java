package com.example.wineyapi.wine.convertor;

import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineyapi.wine.service.WineHelper;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.wine.entity.Wine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class WineConvertor {
    private final WineHelper wineHelper;
    public List<WineResponse.RecommendWineDTO> RecommendWineCountByWine(List<TastingNoteRepository.WineList> wineLists) {
        List<WineResponse.RecommendWineDTO> recommendWineDTOS = new ArrayList<>();

        wineLists.forEach(
                result -> recommendWineDTOS.add(
                        WineResponse.RecommendWineDTO.builder()
                                .name(result.getName())
                                .wineId(result.getWineId())
                                .country(result.getCountry())
                                .price(result.getPrice())
                                .varietal(
                                        result.getVarietal() != null
                                        ? Stream.of(result.getVarietal())
                                        .map(varietal -> varietal.trim())
                                        .collect(Collectors.toList())
                                        : Collections.emptyList())
                                .type(result.getType())
                                .build()
                )
        );

        return recommendWineDTOS;
    }

    public List<WineResponse.RecommendWineDTO> RecommendWineByTastingNote(List<Wine> wineLists) {
        List<WineResponse.RecommendWineDTO> recommendWineDTOS = new ArrayList<>();

        wineLists.forEach(
                result -> recommendWineDTOS.add(
                        WineResponse.RecommendWineDTO.builder()
                                .name(result.getName())
                                .wineId(result.getId())
                                .country(result.getCountry().getValue())
                                .price(result.getPrice() != null ? result.getPrice() : 0)
                                .varietal(
                                        result.getVarietal() != null
                                                ? Stream.of(result.getVarietal())
                                                .map(varietal -> varietal.trim())
                                                .collect(Collectors.toList())
                                                : Collections.emptyList()
                                )
                                .type(result.getType().getValue())
                                .build()
                )
        );

        return recommendWineDTOS;
    }
}
