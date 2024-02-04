package com.example.wineyapi.shop.service.strategy;

import java.util.List;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineydomain.shop.repository.ShopRepository;

public interface ShopListStrategy {

	List<ShopRepository.ShopMapList> getShopList(ShopCommand.getMapCommandDTO commandDTO);

}
