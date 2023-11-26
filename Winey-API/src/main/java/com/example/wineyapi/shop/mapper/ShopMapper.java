package com.example.wineyapi.shop.mapper;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineydomain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {
    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopCommand.getMapCommandDTO toGetShopCommandDTO(ShopReq.MapFilterDto mapFilterDto, User user, ShopFilter shopFilter);
}
