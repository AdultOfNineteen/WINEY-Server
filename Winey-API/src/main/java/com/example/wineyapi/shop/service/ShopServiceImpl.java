package com.example.wineyapi.shop.service;

import com.example.wineyapi.common.dto.Location;
import com.example.wineyapi.common.util.GeoUtils;
import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.converter.ShopConverter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineydomain.shop.repository.ShopRepository;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopConverter shopConverter;

    public List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO commandDTO) {
        ShopFilter shopFilter = commandDTO.getShopFilter();
        ShopReq.MapFilterDto mapFilterDto = commandDTO.getMapFilterDto();

        List<ShopRepository.ShopMapList> shopMapLists;

        switch (shopFilter) {
            case ALL:
                shopMapLists = shopRepository.getShopMapLists(
                    commandDTO.getUser().getId(),
                    mapFilterDto.getLatitude(),
                    mapFilterDto.getLongitude(),
                    mapFilterDto.getLeftTopLatitude(),
                    mapFilterDto.getLeftTopLongitude(),
                    mapFilterDto.getRightBottomLatitude(),
                    mapFilterDto.getRightBottomLongitude()
                );
                break;
            case LIKE:
                shopMapLists = shopRepository.getLikeShopMapLists(
                    commandDTO.getUser().getId(),
                    mapFilterDto.getLatitude(),
                    mapFilterDto.getLongitude(),
                    mapFilterDto.getLeftTopLatitude(),
                    mapFilterDto.getLeftTopLongitude(),
                    mapFilterDto.getRightBottomLatitude(),
                    mapFilterDto.getRightBottomLongitude()
                );
                break;
            default:
                shopMapLists = shopRepository.getFilterShopMapLists(
                    commandDTO.getUser().getId(),
                    shopFilter.toString(),
                    mapFilterDto.getLatitude(),
                    mapFilterDto.getLongitude(),
                    mapFilterDto.getLeftTopLatitude(),
                    mapFilterDto.getLeftTopLongitude(),
                    mapFilterDto.getRightBottomLatitude(),
                    mapFilterDto.getRightBottomLongitude()
                );
                break;
        }

        return shopConverter.toShopMapDtoLists(shopMapLists);
    }
}
