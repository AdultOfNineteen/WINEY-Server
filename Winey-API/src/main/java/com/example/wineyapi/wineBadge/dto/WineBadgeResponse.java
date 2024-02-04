package com.example.wineyapi.wineBadge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
        private String acquisitionMethod;
        private String description;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate acquiredAt;
        private Boolean isRead;
        @Builder.Default
        private String badgeImage = "";
        private String imgUrl;
        private String unActivatedImgUrl;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class WineBadgeListDTO {
       private List<BadgeDTO> sommelierBadgeList;
       private List<BadgeDTO> activityBadgeList;
    }
}
