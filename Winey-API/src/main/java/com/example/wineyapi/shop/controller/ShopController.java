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
import com.example.wineydomain.shop.exception.GetShopErrorCode;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "05-Shop 🗾 와인 판매지 API",description = "와인 판매지 관련 API 태그")
@RequestMapping("/shops")
public class ShopController {
    /*
        와인 판매지 지도 검색 + 필터링
     */
    private final ShopService shopService;
    private final ShopMapper shopMapper = ShopMapper.INSTANCE;
    @Operation(summary = "05-01 와인 판매지 지도 API Made By Austin",description = "와인 판매지 지도 검색 + 필터링 + 상세조회 까지 이 API 로 사용하시면 됩니다.")
    @PostMapping("")
    @ApiErrorCodeExample({UserAuthErrorCode.class, RequestErrorCode.class})
    public CommonResponse<List<ShopRes.ShopMapDto>> getShopMapDtoList(
            @Valid @RequestBody ShopReq.MapFilterDto mapFilterDto,
            @Parameter(description = "필터링", required = true) @RequestParam(required = false,defaultValue = "ALL") ShopFilter shopFilter,
            @AuthenticationPrincipal User user
            ){
        return CommonResponse.onSuccess(shopService.getShopMapDtoList(shopMapper.toGetShopCommandDTO(mapFilterDto, user, shopFilter)));
    }

    @Operation(summary = "05-02 와인 상점 북마크 취소, 북마크 기능 API Made By Austin",description = "와인 상점 북마크 취소, 북마크 기능 API")
    @PostMapping("/bookmark/{shopId}")
    @ApiErrorCodeExample({UserAuthErrorCode.class, GetShopErrorCode.class})
    public CommonResponse<ShopRes.BookmarkDto> bookmarkShop(
            @PathVariable Long shopId,
            @AuthenticationPrincipal User user
    ){
        return CommonResponse.onSuccess(shopService.bookmarkShop(shopId, user));
    }
}
