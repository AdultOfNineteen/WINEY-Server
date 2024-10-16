package com.example.wineyapi.wine.controller;

import com.example.wineyapi.wine.dto.WineRequest;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineyapi.wine.service.WineService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.redis.model.RecommendWineDTO;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import com.example.wineydomain.wine.exception.ReadWineErrorCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "02-Wine🍷",description = "와인 관련 API")
@Slf4j
public class WineController {
    private final WineService wineService;

    @GetMapping("/wines/{wineId}")
    @ApiErrorCodeExample(ReadWineErrorCode.class)
    @Operation(summary= "02-04 Wine🍷 와인 상세조회 #001_01.1_와인 카드 자세히 보기 Made By Peter",description = "와인 상세조회 API입니다.")
    public CommonResponse<WineResponse.WineDTO> getWine(@PathVariable Long wineId) {
        return CommonResponse.onSuccess(wineService.getWineDTOById(wineId));
    }

    @PostMapping("/wines")
    public CommonResponse<WineResponse.CreateWineDTO> createWine(@RequestBody WineRequest.CreateWineDTO request) {
        return null;
    }

    @GetMapping("/wines/recommend")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @Operation(summary= "02-01 Wine🍷 홈화면 와인 조회 API #FRAME 001_01_홈/메인페이지 Made By Austin",description = "홈화면 와인 추천 조회입니다.")
    public CommonResponse<List<RecommendWineDTO>> recommendWine(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return CommonResponse.onSuccess(wineService.recommendWine(user));
    }


    @GetMapping("/wines/search")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @Operation(summary = "02-03 Wine🍷테이스팅 노트 작성용 와인 검색 #FRAME_테이스팅_노트_작성 검색어 입력 안할시 전체 조회입니다. Made By Austin", description = "04-01 사용 전 검색 API 입니다.")
    public CommonResponse<PageResponse<List<WineResponse.SearchWineDto>>> searchWineList(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = false, defaultValue = "10")  Integer size,
            @Parameter(description = "검색어", example = "와인 이름") @RequestParam(required = false) String content
    ){
        return CommonResponse.onSuccess(wineService.searchWineList(page,size,content));
    }
}
