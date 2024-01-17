package com.example.wineyapi.shop.service;

import com.example.wineyapi.common.dto.Location;
import com.example.wineyapi.common.util.GeoUtils;
import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.converter.ShopConverter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineyapi.shop.service.context.ShopListContext;
import com.example.wineyapi.shop.service.context.ShopListContextFactory;
import com.example.wineydomain.shop.repository.ShopRepository;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopConverter shopConverter;

    public List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO commandDTO) {
        ShopListContext shopListContext = ShopListContextFactory.getShopListContext(commandDTO.getShopFilter());
        List<ShopRepository.ShopMapList> shopMapLists = shopListContext.getShopList(commandDTO);
        return shopConverter.toShopMapDtoLists(shopMapLists);
    }
}
