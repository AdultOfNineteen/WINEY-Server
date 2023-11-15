package com.example.wineyapi.shop.service;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.converter.ShopConverter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineydomain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopConverter shopConverter;

    public List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO commandDTO) {
        List<ShopRepository.ShopMapList> shopMapLists;
        if(commandDTO.getShopFilter().equals(ShopFilter.ALL)){
            shopMapLists = shopRepository.getAllShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else if(commandDTO.getShopFilter().equals(ShopFilter.LIKE)){
            shopMapLists = shopRepository.getLikeShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else{
            shopMapLists = shopRepository.getFilterShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude(), commandDTO.getShopFilter().toString());
        }
        return shopConverter.toShopMapDtoLists(shopMapLists);
    }
}
