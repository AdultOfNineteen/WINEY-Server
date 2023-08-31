package com.example.wineyapi.preference.service;

import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineydomain.preference.entity.Preference;
import com.example.wineydomain.preference.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {
    private final PreferenceRepository preferenceRepository;

    @Override
    public Preference update(Long userId, PreferenceRequest.UpdatePreferenceDTO request) {
        return null;
    }
}
