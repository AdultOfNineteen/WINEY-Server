package com.example.wineyapi.admin.shop.controller;

import com.example.wineyapi.admin.shop.dto.ShopReq;
import com.example.wineyapi.admin.shop.service.AdminShopService;
import com.example.wineycommon.reponse.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/shops")
public class AdminShopController {
    private final AdminShopService adminShopService;
    @PostMapping("")
    public CommonResponse<String> uploadShops(@RequestBody List<ShopReq.ShopUploadDTO> shopUploadDTO){
        adminShopService.uploadShops(shopUploadDTO);
        return CommonResponse.onSuccess("성공");
    }

}
