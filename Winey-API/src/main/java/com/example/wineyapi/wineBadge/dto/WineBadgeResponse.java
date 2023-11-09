package com.example.wineyapi.wineBadge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class WineBadgeResponse {

    private WineBadgeResponse() {}

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class BadgeDTO {
        private Long badgeId;
        private String badgeType;
        private String name;
        private LocalDateTime acquiredAt;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineBadgeListDTO {
       private List<BadgeDTO> sommelierBadgeList;
       private List<BadgeDTO> activitybadgeDTOList;
    }
}
