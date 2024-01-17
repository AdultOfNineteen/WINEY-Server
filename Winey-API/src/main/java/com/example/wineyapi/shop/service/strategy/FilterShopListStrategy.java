package com.example.wineyapi.shop.service.strategy;

import java.util.List;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineydomain.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FilterShopListStrategy implements ShopListStrategy{
	private final ShopRepository shopRepository;

	@Override
	public List<ShopRepository.ShopMapList> getShopList(ShopCommand.getMapCommandDTO commandDTO) {
		ShopReq.MapFilterDto mapFilterDto = commandDTO.getMapFilterDto();
		return shopRepository.getFilterShopMapLists(
			commandDTO.getUser().getId(),
			commandDTO.getShopFilter().toString(),
			mapFilterDto.getLatitude(),
			mapFilterDto.getLongitude(),
			mapFilterDto.getLeftTopLatitude(),
			mapFilterDto.getLeftTopLongitude(),
			mapFilterDto.getRightBottomLatitude(),
			mapFilterDto.getRightBottomLongitude()
		);
	}
}
