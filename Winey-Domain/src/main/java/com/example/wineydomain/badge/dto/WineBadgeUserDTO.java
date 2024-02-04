package com.example.wineydomain.badge.dto;

import com.example.wineydomain.badge.entity.BadgeType;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.badge.entity.WineBadge;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class WineBadgeUserDTO {
    private Long wineBadgeId;
    private String badge;
    private String name;
    private BadgeType type;
    private String description;
    private Integer requiredActivity;
    private Long userId;
    private Boolean isRead;
    private String acquisitionMethod;
    private LocalDateTime acquiredAt;
    private String imgUrl;
    private String unActivatedImgUrl;

    public static WineBadgeUserDTO from(WineBadge wineBadge) {
        return WineBadgeUserDTO.builder()
                .wineBadgeId(wineBadge.getId())
                .badge(wineBadge.getBadge())
                .name(wineBadge.getName())
                .type(wineBadge.getType())
                .description(wineBadge.getDescription())
                .requiredActivity(wineBadge.getRequiredActivity())
                .acquisitionMethod(wineBadge.getAcquisitionMethod())
                .userId(null)
                .isRead(null)
                .acquiredAt(null)
                .imgUrl(wineBadge.getImgUrl())
                .unActivatedImgUrl(wineBadge.getUnActivatedImgUrl())
                .build();
    }

    public static WineBadgeUserDTO from(UserWineBadge userWineBadge) {
        WineBadge wineBadge = userWineBadge.getWineBadge();
        return WineBadgeUserDTO.builder()
                .wineBadgeId(wineBadge.getId())
                .badge(wineBadge.getBadge())
                .name(wineBadge.getName())
                .type(wineBadge.getType())
                .description(wineBadge.getDescription())
                .requiredActivity(wineBadge.getRequiredActivity())
                .acquisitionMethod(wineBadge.getAcquisitionMethod())
                .userId(userWineBadge.getUser().getId())
                .isRead(userWineBadge.getIsRead())
                .acquiredAt(userWineBadge.getCreatedAt())
                .imgUrl(wineBadge.getImgUrl())
                .unActivatedImgUrl(wineBadge.getUnActivatedImgUrl())
                .build();
    }

}
