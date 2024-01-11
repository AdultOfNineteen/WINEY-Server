package com.example.wineyapi.shop.converter;

import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineydomain.shop.entity.Mood;
import com.example.wineydomain.shop.repository.ShopRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ShopConverter {
    public List<ShopRes.ShopMapDto> toShopMapDtoLists(List<ShopRepository.ShopMapList> shopMapLists) {
        return shopMapLists.stream()
            .map(this::toShopMapDto)
            .collect(Collectors.toList());
    }

    private ShopRes.ShopMapDto toShopMapDto(ShopRepository.ShopMapList result) {
        List<String> shopMoodsList = Optional.ofNullable(result.getShopMoods())
            .map(moods -> Arrays.stream(moods.split(","))
                .map(String::trim)
                .map(Mood::valueOf)
                .map(Mood::getValue)
                .collect(Collectors.toList())
            )
            .orElse(Collections.emptyList());

        return ShopRes.ShopMapDto.builder()
            .shopId(result.getShopId())
            .isLike(result.getBookMark())
            .latitude(result.getLatitude())
            .longitude(result.getLongitude())
            .businessHour(result.getBusinessHour())
            .imgUrl(result.getImgUrl())
            .address(result.getAddress())
            .phone(result.getPhone())
            .name(result.getName())
            .meter(result.getMeter())
            .shopType(result.getShopType().getType())
            .shopMoods(shopMoodsList)
            .build();
    }
}
