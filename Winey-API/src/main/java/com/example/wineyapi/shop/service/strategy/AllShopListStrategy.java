package com.example.wineyapi.shop.service.strategy;

import java.util.List;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineydomain.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllShopListStrategy implements ShopListStrategy{
	private final ShopRepository shopRepository;

	@Override
	public List<ShopRepository.ShopMapList> getShopList(ShopCommand.getMapCommandDTO commandDTO) {
		ShopReq.MapFilterDto mapFilterDto = commandDTO.getMapFilterDto();
		return shopRepository.getShopMapLists(
			commandDTO.getUser().getId(),
			mapFilterDto.getLatitude(),
			mapFilterDto.getLongitude(),
			mapFilterDto.getLeftTopLatitude(),
			mapFilterDto.getLeftTopLongitude(),
			mapFilterDto.getRightBottomLatitude(),
			mapFilterDto.getRightBottomLongitude()
		);
	}
}
