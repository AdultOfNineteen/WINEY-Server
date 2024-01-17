package com.example.wineyapi.shop.service.context;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineyapi.shop.service.strategy.AllShopListStrategy;
import com.example.wineyapi.shop.service.strategy.FilterShopListStrategy;
import com.example.wineyapi.shop.service.strategy.LikeShopListStrategy;
import com.example.wineyapi.shop.service.strategy.ShopListStrategy;
import com.example.wineydomain.shop.repository.ShopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ShopListContextFactory {
	private final ShopRepository shopRepository;

	private static ShopListContext allShopListContext;

	private static ShopListContext likeShopListContext;

	private static ShopListContext filterShopListContext;

	@PostConstruct
	private void init() {
		allShopListContext = createContext(new AllShopListStrategy(shopRepository));
		likeShopListContext = createContext(new LikeShopListStrategy(shopRepository));
		filterShopListContext = createContext(new FilterShopListStrategy(shopRepository));
	}

	private static ShopListContext createContext(ShopListStrategy shopListStrategy) {
		return new ShopListContext(shopListStrategy);
	}

	public static ShopListContext getShopListContext(ShopFilter filter) {
		switch (filter){
			case ALL:
				return allShopListContext;
			case LIKE:
				return likeShopListContext;
			default:
				return filterShopListContext;
		}
	}

}
