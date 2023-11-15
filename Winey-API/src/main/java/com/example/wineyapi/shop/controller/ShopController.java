package com.example.wineyapi.shop.controller;

import com.example.wineyapi.shop.dto.ShopCommand;
import com.example.wineyapi.shop.dto.ShopReq;
import com.example.wineyapi.shop.dto.ShopRes;
import com.example.wineyapi.shop.mapper.ShopMapper;
import com.example.wineyapi.shop.service.ShopService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.exception.errorcode.RequestErrorCode;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.shop.entity.ShopType;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "05-Shop 🗾 와인 판매지 API",description = "와인 판매지 관련 API 태그")
@RequestMapping("/admin/shops")
public class ShopController {
    /*
        와인 판매지 지도 검색 + 필터링
     */
    private final ShopService shopService;
    private final ShopMapper shopMapper = ShopMapper.INSTANCE;
    @Operation(summary = "05-01 와인 판매지 지도 API Made By Austin",description = "와인 판매지 지도 검색 + 필터링 API")
    @GetMapping("")
    @ApiErrorCodeExample({UserAuthErrorCode.class, RequestErrorCode.class})
    public CommonResponse<List<ShopRes.ShopMapDto>> getShopMapDtoList(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @Parameter(description = "필터링", required = true) @RequestParam(required = false,defaultValue = "ALL") ShopFilter shopFilter,
            @AuthenticationPrincipal User user
            ){

        ShopCommand.getMapCommandDTO getMapCommandDTO = shopMapper.toGetShopCommandDTO(latitude, longitude, user, shopFilter);
        return CommonResponse.onSuccess(shopService.getShopMapDtoList(getMapCommandDTO));
    }


    /*
        와인 판매지 하단 스와이프 조회 + 필터링
     */

    /*
        와인 판매지 북마크 + 북마크 취소
     */

    /*
         와인 판매지 하단 스와이프 클릭 시 상세 조회
     */
}
