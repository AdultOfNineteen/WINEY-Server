package com.example.wineyapi.preference.dto;

import lombok.Getter;

public class PreferenceRequest {

    @Getter
    public static class UpdatePreferenceDTO {
        private String field;
    }
}
