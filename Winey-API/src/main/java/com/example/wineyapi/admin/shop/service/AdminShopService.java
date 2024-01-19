package com.example.wineyapi.admin.shop.service;

import static com.example.wineyinfrastructure.amazonS3.enums.Folder.*;

import com.example.wineyapi.admin.shop.converter.AdminShopConverter;
import com.example.wineyapi.admin.shop.dto.ShopReq;
import com.example.wineydomain.shop.entity.Mood;
import com.example.wineydomain.shop.entity.Shop;
import com.example.wineydomain.shop.entity.ShopMood;
import com.example.wineydomain.shop.repository.ShopMoodRepository;
import com.example.wineydomain.shop.repository.ShopRepository;
import com.example.wineyinfrastructure.amazonS3.service.S3UploadService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminShopService {
    private final ShopRepository shopRepository;
    private final ShopMoodRepository shopMoodRepository;
    private final AdminShopConverter adminShopConverter;
    private final S3UploadService s3UploadService;


    @Transactional
    public void uploadShops(List<ShopReq.ShopUploadDTO> shopUploadReqs) {
        List<Shop> shops = new ArrayList<>();
        for (ShopReq.ShopUploadDTO req : shopUploadReqs){
            Shop shop  = shopRepository.save(adminShopConverter.convertToShop(req));
            if (req.getMoods()!=null) {
                List<ShopMood> shopMoods = new ArrayList<>();
                for (Mood mood : req.getMoods()){
                    shopMoods.add(adminShopConverter.convertToShopMood(mood, shop));
                }
                shopMoodRepository.saveAll(shopMoods);
            }

        }
    }

	public void uploadImage(Long shopId, MultipartFile multipartFile) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        String imageUrl = s3UploadService.uploadImage(shopId, SHOP, multipartFile);
        shop.setImgUrl(imageUrl);
        shopRepository.save(shop);
	}
}
