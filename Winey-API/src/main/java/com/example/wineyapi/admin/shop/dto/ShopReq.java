package com.example.wineyapi.admin.shop.dto;

import com.example.wineydomain.shop.entity.Mood;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopMood;
import com.example.wineydomain.shop.entity.ShopType;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.List;

public class ShopReq {
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ShopUploadReq{
        private String name;

        private ShopType shopType;

        private String address;

        private String phone;

        private String businessHour;

        private String imgUrl;

        private Double latitude;

        private Double longitude;

        private List<Mood> moods;
    }
}
