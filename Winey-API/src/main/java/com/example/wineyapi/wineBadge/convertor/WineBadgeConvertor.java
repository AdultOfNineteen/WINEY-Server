package com.example.wineyapi.wineBadge.convertor;

import com.example.wineyapi.wineBadge.dto.WineBadgeResponse;
import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WineBadgeConvertor {

    public static WineBadgeResponse.BadgeDTO toBadgeDTO(UserWineBadge userWineBadge) {
        return WineBadgeResponse.BadgeDTO.builder()
                .badgeId(userWineBadge.getId())
                .badgeType(userWineBadge.getBadge().getType())
                .name(userWineBadge.getBadge().getBadgeName())
                .description(userWineBadge.getBadge().getBadgeDescription())
                .acquiredAt(userWineBadge.getCreatedAt())
                .build();
    }

    public static WineBadgeResponse.WineBadgeListDTO toWineBadgeListDTO(List<UserWineBadge> userWineBadgeList) {
        List<WineBadgeResponse.BadgeDTO> sommelierBadgeList = userWineBadgeList.stream()
                .filter(userWineBadge -> userWineBadge.getBadge().getType().equals("소믈리에 배지"))
                .map(WineBadgeConvertor::toBadgeDTO)
                .collect(Collectors.toList());

        List<WineBadgeResponse.BadgeDTO> activityBadgeList = userWineBadgeList.stream()
                .filter(userWineBadge -> userWineBadge.getBadge().getType().equals("활동 배지"))
                .map(WineBadgeConvertor::toBadgeDTO)
                .collect(Collectors.toList());

        return WineBadgeResponse.WineBadgeListDTO.builder()
                .sommelierBadgeList(sommelierBadgeList)
                .activitybadgeDTOList(activityBadgeList)
                .build();
    }

    public UserWineBadge WineBadge(Badge badge, User user) {
        return UserWineBadge
                .builder()
                .badge(badge)
                .user(user)
                .build();
    }
}
