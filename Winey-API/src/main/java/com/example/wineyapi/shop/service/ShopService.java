package com.example.wineyapi.shop.service;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineydomain.user.entity.User;

import java.util.List;

public interface ShopService {
    List<ShopRes.ShopMapDto> getShopMapDtoList(ShopCommand.getMapCommandDTO getMapCommandDTO);

    List<ShopRes.ShopMapDto> getShopMapDtoLists(ShopReq.MapFilterDto mapFilterDto, User user, ShopFilter shopFilter);
}
