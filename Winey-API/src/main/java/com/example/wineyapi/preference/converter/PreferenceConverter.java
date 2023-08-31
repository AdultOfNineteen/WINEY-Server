package com.example.wineyapi.preference.converter;

import com.example.wineyapi.preference.dto.PreferenceResponse;
import com.example.wineydomain.preference.entity.Preference;

public class PreferenceConverter {
    public static PreferenceResponse.UpdatePreferenceDTO toUpdatePreferenceDTO(Preference updatedPreference) {
        return PreferenceResponse.UpdatePreferenceDTO.builder()
                .preferenceId(updatedPreference.getId())
                .updatedAt(updatedPreference.getUpdatedAt())
                .build();
    }
}
