package com.example.wineyapi.admin.wineTip.controller;

import com.example.wineyapi.admin.wineTip.dto.WineTipReq;
import com.example.wineyapi.wineTip.service.WineTipService;
import com.example.wineycommon.annotation.ApiErrorCodeExample;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/wine-tip")
@RequiredArgsConstructor
public class AdminWineTipController {
    private final WineTipService wineTipService;
    @Operation(summary = "WineTip 업로드 API 입니다. multipart/form-data 형식")
    @RequestMapping(value = "", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @ApiErrorCodeExample(UserAuthErrorCode.class)
    public CommonResponse<String> postWineTipImg(
            @ModelAttribute WineTipReq.WineTipDto wineTipDto){
        wineTipService.uploadWineTip(wineTipDto);
        return CommonResponse.onSuccess(null);
    }
}
