package com.example.wineyapi.wine.controller;

import com.example.wineyapi.wine.dto.WineRequest;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineyapi.wine.service.WineService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public CommonResponse<List<WineResponse.RecommendWineDTO>> recommendWine(@Parameter(hidden = true) @AuthenticationPrincipal User user) {
        return CommonResponse.onSuccess(wineService.recommendWine(user));
    }
}
