package com.example.wineyapi.admin.shop.converter;

import com.example.wineyapi.admin.shop.dto.ShopReq;
import com.example.wineydomain.shop.entity.Mood;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopMood;
import org.springframework.stereotype.Component;


@Component
public class AdminShopConverter {
    public Shop convertToShop(ShopReq.ShopUploadDTO req) {
            return Shop
                    .builder()
                    .name(req.getName())
                    .shopType(req.getShopType())
                    .address(req.getAddress())
                    .phone(req.getPhone())
                    .businessHour(req.getBusinessHour())
                    .imgUrl(req.getImgUrl())
                    .latitude(req.getLatitude())
                    .longitude(req.getLongitude())
                    .build();

    }

    public ShopMood convertToShopMood(Mood mood, Shop shop) {
        return ShopMood
                .builder()
                .mood(mood)
                .shop(shop)
                .build();
    }
}
