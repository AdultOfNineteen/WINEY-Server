package com.example.wineyapi.wineBadge.convertor;

import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.entity.WineBadge;
import com.example.wineydomain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class WineBadgeConvertor {
    public UserWineBadge WineBadge(Badge badge, User user) {
        return UserWineBadge
                .builder()
                .badge(badge)
                .user(user)
                .build();
    }
}
