package com.example.wineyapi.common;

import com.example.wineyapi.wineBadge.service.WineBadgeService;
import com.example.wineycommon.annotation.RedissonLock;
import com.example.wineydomain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final WineBadgeService wineBadgeService;

    @GetMapping("/badge")
    public String checkWineBadge(@AuthenticationPrincipal User user){
        wineBadgeService.calculateBadge(user, user.getId());
        return "체크";
    }
}
