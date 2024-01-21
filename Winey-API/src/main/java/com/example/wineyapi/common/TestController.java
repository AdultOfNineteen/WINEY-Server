package com.example.wineyapi.common;

import com.example.wineyapi.security.JwtService;
import com.example.wineyapi.wineBadge.service.WineBadgeService;
import com.example.wineycommon.annotation.RedissonLock;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final WineBadgeService wineBadgeService;
    private final JwtService jwtService;

    @GetMapping("/badge")
    public String checkWineBadge(@AuthenticationPrincipal User user){
        wineBadgeService.calculateBadge(user, user.getId());
        return "체크";
    }
    @GetMapping("/{userId}")
    public CommonResponse<String> createToken(@PathVariable Long userId){
        String accessToken = jwtService.createToken(userId);
        String refreshToken = jwtService.createRefreshToken(userId);
        return CommonResponse.onSuccess(accessToken + " " + refreshToken);
    }

}
