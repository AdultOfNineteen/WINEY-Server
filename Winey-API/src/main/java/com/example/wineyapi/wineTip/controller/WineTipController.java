package com.example.wineyapi.wineTip.controller;

import com.example.wineyapi.wineTip.dto.WineTipResponse;
import com.example.wineyapi.wineTip.service.WineTipService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineycommon.reponse.PageResponse;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wine-tip")
@Tag(name = "💡WineTip" , description = "와인팁 관련 API 입니다.")
@RequiredArgsConstructor
@Slf4j
public class WineTipController {
    private final WineTipService wineTipService;
    @GetMapping("")
    @Operation(summary = "WineTip 리스트 조회")
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<PageResponse<List<WineTipResponse.WineTipDto>>> getWineTip(
            @Parameter(description = "페이지", example = "0") @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = false, defaultValue = "10")  Integer size
    ){
        return CommonResponse.onSuccess(wineTipService.getWineTip(page, size));
    }

}
