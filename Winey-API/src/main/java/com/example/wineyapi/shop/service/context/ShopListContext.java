package com.example.wineyapi.shop.service.context;

import java.util.List;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.service.strategy.ShopListStrategy;
import com.example.wineydomain.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopListContext {
	private final ShopListStrategy shopListStrategy;

	public List<ShopRepository.ShopMapList> getShopList(ShopCommand.getMapCommandDTO commandDTO) {
		return shopListStrategy.getShopList(commandDTO);
	}
}
