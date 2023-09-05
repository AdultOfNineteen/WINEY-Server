package com.example.wineyapi.admin.wineTip.controller;

import com.example.wineyapi.admin.wineTip.dto.WineTipReq;
import com.example.wineycommon.reponse.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/wine-tip")
public class WineTipController {
    @PostMapping("")
    @Operation(summary = "WineTip 업로드")
    public CommonResponse<String> createWineTip(@RequestBody WineTipReq.WineTipDto wineTip){
        return CommonResponse.onSuccess("업로드 성공");
    }

    @PostMapping(value = "", consumes = {"multipart/form-data"}, produces = "application/json")
    public CommonResponse<List<String>> postWineTipImg(
            @RequestPart("storeImg") List<MultipartFile> storeImg){
        return CommonResponse.onSuccess(null);
    }
}
