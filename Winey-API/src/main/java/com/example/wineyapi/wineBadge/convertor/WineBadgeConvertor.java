package com.example.wineyapi.wineBadge.convertor;

import com.example.wineyapi.wineBadge.dto.WineBadgeResponse;
import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.entity.WineBadge;
import com.example.wineydomain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WineBadgeConvertor {

    public static WineBadgeResponse.WineBadgeListDTO toWineBadgeListDTO(List<UserWineBadge> userWineBadgeList) {
        return null;
    }

    public UserWineBadge WineBadge(Badge badge, User user) {
        return UserWineBadge
                .builder()
                .badge(badge)
                .user(user)
                .build();
    }
}
