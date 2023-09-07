package com.example.wineyapi.wine.controller;

import com.example.wineyapi.wine.dto.WineRequest;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineyapi.wine.service.WineService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
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
    public CommonResponse<WineResponse.WineDTO> getWine(@PathVariable Long wineId) {
        return null;
    }

    @GetMapping("/wines")
    public CommonResponse<WineResponse.WineListDTO> getWineList(@RequestParam String name) {
        return null;
    }

    @PostMapping("/wines")
    public CommonResponse<WineResponse.CreateWineDTO> createWine(@RequestBody WineRequest.CreateWineDTO request) {
        return null;
    }
    @GetMapping("/wines/recommend")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @Operation(summary= "02-01 Wine🍷 홈화면 와인 조회",description = "홈화면 와인 추천 조회입니다.")
    public CommonResponse<List<WineResponse.RecommendWineDTO>> recommendWine(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return CommonResponse.onSuccess(wineService.recommendWine(user));
    }

    @GetMapping("/wines/taste-analysis")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    @Operation(summary= "02-02 내 취향 분석",description = "내 취향 분석 API 입니다")
    public CommonResponse<WineResponse.TasteAnalysisDTO> tasteAnalysis(@Parameter(hidden = true) @AuthenticationPrincipal User user){
        return CommonResponse.onSuccess(wineService.tasteAnalysis(user));
    }
}
