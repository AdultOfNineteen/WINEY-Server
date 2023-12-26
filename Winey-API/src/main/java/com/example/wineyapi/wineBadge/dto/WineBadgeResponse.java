package com.example.wineyapi.wineBadge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime acquiredAt;
        // TODO : 읽음 여부 추가
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
