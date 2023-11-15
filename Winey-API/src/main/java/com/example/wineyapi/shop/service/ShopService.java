package com.example.wineyapi.shop.service;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopRes;

import java.util.List;

public interface ShopService {
    List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO getMapCommandDTO);
}
