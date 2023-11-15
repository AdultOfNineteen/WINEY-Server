package com.example.wineyapi.shop.dto;

import com.example.wineyapi.shop.controller.ShopFilter;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopType;
import com.example.wineydomain.user.entity.User;
import lombok.*;

public class ShopCommand {
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class getMapCommandDTO{
         private double latitude;

         private double longitude;

         private ShopFilter shopFilter;

         private User user;
    }
}
