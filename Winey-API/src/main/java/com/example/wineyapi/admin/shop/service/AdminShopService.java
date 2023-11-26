package com.example.wineyapi.admin.shop.service;

import com.example.wineyapi.admin.shop.converter.AdminShopConverter;
import com.example.wineyapi.admin.shop.dto.ShopReq;
import com.example.wineydomain.shop.entity.Mood;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopMood;
import com.example.wineydomain.shop.repository.ShopMoodRepository;
import com.example.wineydomain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminShopService {
    private final ShopRepository shopRepository;
    private final ShopMoodRepository shopMoodRepository;
    private final AdminShopConverter adminShopConverter;


    @Transactional
    public void uploadShops(List<ShopReq.ShopUploadDTO> shopUploadReqs) {
        List<Shop> shops = new ArrayList<>();
        for (ShopReq.ShopUploadDTO req : shopUploadReqs){
            Shop shop  = shopRepository.save(adminShopConverter.convertToShop(req));
            System.out.println(req.getLongitude());
            System.out.println(req.getLatitude());
            shopRepository.updateShopPoint(String.format("POINT(%f %f)", req.getLatitude(), req.getLongitude()), shop.getId());
            if (req.getMoods()!=null) {
                List<ShopMood> shopMoods = new ArrayList<>();
                for (Mood mood : req.getMoods()){
                    shopMoods.add(adminShopConverter.convertToShopMood(mood, shop));
                }
                shopMoodRepository.saveAll(shopMoods);
            }

        }
    }
}
