package com.example.wineyapi.shop.mapper;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineydomain.user.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-30T19:14:10+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.19 (Oracle Corporation)"
)
public class ShopMapperImpl implements ShopMapper {

    @Override
    public ShopCommand.getMapCommandDTO toGetShopCommandDTO(ShopReq.MapFilterDto mapFilterDto, User user, ShopFilter shopFilter) {
        if ( mapFilterDto == null && user == null && shopFilter == null ) {
            return null;
        }

        ShopCommand.getMapCommandDTO.getMapCommandDTOBuilder getMapCommandDTO = ShopCommand.getMapCommandDTO.builder();

        getMapCommandDTO.mapFilterDto( mapFilterDto );
        getMapCommandDTO.user( user );
        getMapCommandDTO.shopFilter( shopFilter );

        return getMapCommandDTO.build();
    }
}
