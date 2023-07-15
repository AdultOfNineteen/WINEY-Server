package com.example.wineyapi.wineBadge.controller;

import com.example.wineyapi.wineBadge.dto.WineBadgeResponse;
import com.example.wineycommon.reponse.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WineBadgeController {

    @GetMapping("/users/{userId}/wine-badges")
    public CommonResponse<WineBadgeResponse.WineBadgeListDTO> getWineBadge(@PathVariable Long userId) {
        return null;
    }
}
