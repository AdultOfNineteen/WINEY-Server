package com.example.wineyapi.wineBadge.convertor;

import com.example.wineyapi.wineBadge.dto.WineBadgeResponse;
import com.example.wineydomain.badge.dto.WineBadgeUserDTO;
import com.example.wineydomain.badge.entity.Badge;
import com.example.wineydomain.badge.entity.BadgeType;
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
                .isRead(userWineBadge.getIsRead())
                .build();
    }

    public static WineBadgeResponse.BadgeDTO toBadgeDTO(WineBadgeUserDTO userWineBadge) {
        return WineBadgeResponse.BadgeDTO.builder()
                .badgeId(userWineBadge.getWineBadgeId())
                .badgeType(userWineBadge.getType().name())
                .name(userWineBadge.getName())
                .description(userWineBadge.getDescription())
                .acquisitionMethod(userWineBadge.getAcquisitionMethod())
                .acquiredAt(userWineBadge.getAcquiredAt())
                .isRead(userWineBadge.getIsRead())
                .build();
    }

    public static WineBadgeResponse.WineBadgeListDTO toWineBadgeListDTO(List<WineBadgeUserDTO> userWineBadgeList) {
        List<WineBadgeResponse.BadgeDTO> sommelierBadgeList = userWineBadgeList.stream()
                .filter(wineBadgeUserDTO -> wineBadgeUserDTO.getType() == BadgeType.SOMMELIER)
                .map(WineBadgeConvertor::toBadgeDTO)
                .collect(Collectors.toList());

        List<WineBadgeResponse.BadgeDTO> activityBadgeList = userWineBadgeList.stream()
                .filter(wineBadgeUserDTO -> wineBadgeUserDTO.getType() == BadgeType.ACTIVITY)
                .map(WineBadgeConvertor::toBadgeDTO)
                .collect(Collectors.toList());

        return WineBadgeResponse.WineBadgeListDTO.builder()
                .sommelierBadgeList(sommelierBadgeList)
                .activityBadgeList(activityBadgeList)
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
