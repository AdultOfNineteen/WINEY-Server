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
        List<ShopRepository.ShopMapList> shopMapLists = null;
        /*if(commandDTO.getShopFilter().equals(ShopFilter.ALL)){
            shopMapLists = shopRepository.getAllShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else if(commandDTO.getShopFilter().equals(ShopFilter.LIKE)){
            shopMapLists = shopRepository.getLikeShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else{
            shopMapLists = shopRepository.getFilterShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude(), commandDTO.getShopFilter().toString());
        }*/

        ShopReq.MapFilterDto mapFilterDto = commandDTO.getMapFilterDto();

        System.out.println(mapFilterDto.toString());

        Location northEast = GeoUtils.calculate(mapFilterDto.getLatitude(), mapFilterDto.getLongitude(), 2.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(x, y, 2.0, Direction.SOUTHWEST.getBearing());



        if(commandDTO.getShopFilter().equals(ShopFilter.ALL)){
            shopMapLists = shopRepository.getShopMapLists(commandDTO.getUser().getId(), mapFilterDto.getLatitude(), mapFilterDto.getLongitude(), mapFilterDto.getLeftTopLatitude(), mapFilterDto.getLeftTopLongitude(), mapFilterDto.getRightBottomLatitude(), mapFilterDto.getRightBottomLongitude());
        }
        else if(commandDTO.getShopFilter().equals(ShopFilter.LIKE)){
            //shopMapLists = shopRepository.getLikeShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else{
            //shopMapLists = shopRepository.getFilterShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude(), commandDTO.getShopFilter().toString());
        }
        return shopConverter.toShopMapDtoLists(shopMapLists);
    }

    @Override
    public List<ShopRes.ShopMapDto> getShopMapDtoLists(ShopReq.MapFilterDto mapFilterDto, User user, ShopFilter shopFilter) {
        List<ShopRepository.ShopMapList> shopMapLists = null;


        if(shopFilter.equals(ShopFilter.ALL)){
            shopMapLists = shopRepository.getShopMapLists(user.getId(), mapFilterDto.getLatitude(), mapFilterDto.getLongitude(), mapFilterDto.getLeftTopLatitude(), mapFilterDto.getLeftTopLongitude(), mapFilterDto.getRightBottomLatitude(), mapFilterDto.getRightBottomLongitude());
        }
        else if(shopFilter.equals(ShopFilter.LIKE)){
            //shopMapLists = shopRepository.getLikeShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude());
        }
        else{
            //shopMapLists = shopRepository.getFilterShopMapLists(commandDTO.getUser().getId(), commandDTO.getLatitude(), commandDTO.getLongitude(), commandDTO.getShopFilter().toString());
        }
        return shopConverter.toShopMapDtoLists(shopMapLists);    }
}
