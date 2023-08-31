package com.example.wineyapi.preference.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PreferenceResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class PreferenceDTO {
        private Long field;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class UpdatePreferenceDTO {
        private Long preferenceId;
        private LocalDateTime updatedAt;
    }
}
