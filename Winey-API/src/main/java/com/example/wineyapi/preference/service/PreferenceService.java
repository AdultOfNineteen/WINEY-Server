package com.example.wineyapi.preference.service;

import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineydomain.preference.entity.Preference;

public interface PreferenceService {
    Preference update(Long userId, PreferenceRequest.UpdatePreferenceDTO request);
}
