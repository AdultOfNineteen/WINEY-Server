package com.example.wineyapi.wine.controller;

import com.example.wineyapi.wine.dto.WineRequest;
import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineycommon.reponse.CommonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class WineController {

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
}
