package com.example.wineyapi.shop.converter;

import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineydomain.shop.repository.ShopRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShopConverter {
    public List<ShopRes.ShopMapDto> toShopMapDtoLists(List<ShopRepository.ShopMapList> shopMapLists) {
        List<ShopRes.ShopMapDto> shopMapDtos = new ArrayList<>();

        shopMapLists.forEach(
                result -> {
                    shopMapDtos.add(toShopMapDto(result));
                }
        );

        return shopMapDtos;
    }

    private ShopRes.ShopMapDto toShopMapDto(ShopRepository.ShopMapList result) {
        return ShopRes.ShopMapDto
                .builder()
                .shopId(result.getShopId())
                .isLike(result.getLikes())
                .name(result.getName())
                .meter(result.getMeter())
                .build();
    }
}
