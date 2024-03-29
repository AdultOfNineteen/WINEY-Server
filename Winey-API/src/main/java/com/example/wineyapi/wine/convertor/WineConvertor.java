package com.example.wineyapi.wine.convertor;

import com.example.wineyapi.common.redis.RecommendWine;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineyapi.wine.service.WineHelper;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.tastingNote.repository.TastingNoteRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.repository.WineRepository;
import com.example.wineydomain.wine.entity.WineSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public List<WineResponse.RecommendWineDTO> toRecommendWineDto(List<WineRepository.WineList> wineLists) {
        List<WineResponse.RecommendWineDTO> recommendWineDTOS = new ArrayList<>();

        wineLists.forEach(
                result -> recommendWineDTOS.add(
                        WineResponse.RecommendWineDTO.builder()
                                .wineId(result.getId())
                                .name(result.getName())
                                .country(result.getCountry())
                                .price(result.getPrice())
                                .varietal(
                                        result.getVarietal() != null
                                                ? Stream.of(result.getVarietal())
                                                .map(varietal -> varietal.trim())
                                                .collect(Collectors.toList())
                                                : Collections.emptyList()
                                )
                                .type(result.getType())
                                .build()
                )
        );

        return recommendWineDTOS;
    }

    public PageResponse<List<WineResponse.SearchWineDto>> SearchWineList(Page<Wine> wines) {
        List<WineResponse.SearchWineDto> wineDtoList = new ArrayList<>();

        wines.getContent().forEach(
                result -> wineDtoList.add(
                        convertWineInfo(result)
                )
        );
        return new PageResponse<>(wines.isLast(), wines.getTotalElements(), wineDtoList);
    }

    private WineResponse.SearchWineDto convertWineInfo(Wine result) {
        return WineResponse.SearchWineDto
                .builder()
                .wineId(result.getId())
                .country(result.getCountry())
                .type(result.getType())
                .name(result.getName())
                .varietal(result.getVarietal())
                .build();
    }

    public WineResponse.WineDTO toWineDTO(Wine wine, WineSummary wineSummary) {
        return WineResponse.WineDTO.builder()
                .wineId(wine.getId())
                .type(wine.getType().name()) // Enum의 name() 메서드를 호출하여 문자열로 변환
                .name(wine.getName())
                .country(wine.getCountry().name()) // Enum의 name() 메서드를 호출하여 문자열로 변환
                .varietal(wine.getVarietal())
                .sweetness(wine.getSweetness())
                .acidity(wine.getAcidity())
                .body(wine.getBody())
                .tannins(wine.getTannins())
                .wineSummary(wineSummary)
                .build();
    }

    public RecommendWine toRecommendWine(User user, List<WineResponse.RecommendWineDTO> recommendWineLists) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay();

        return RecommendWine.builder()
                .id(String.valueOf(user.getId()))
                .recommendWineList(recommendWineLists)
                .ttl(Duration.between(now, midnight).getSeconds())
                .build();
    }
}
