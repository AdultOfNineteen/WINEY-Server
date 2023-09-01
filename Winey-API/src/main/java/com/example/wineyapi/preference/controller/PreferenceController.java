package com.example.wineyapi.preference.controller;

import com.example.wineyapi.preference.converter.PreferenceConverter;
import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineyapi.preference.dto.PreferenceResponse;
import com.example.wineyapi.preference.service.PreferenceService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.preference.entity.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.PreferenceDTO> getPreference(@PathVariable Long userId) {
        return null;
    }

    @PatchMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.UpdatePreferenceDTO> updatePreference(@PathVariable Long userId,
                                                                                   @RequestBody PreferenceRequest.UpdatePreferenceDTO request) {
        Preference updatedPreference = preferenceService.update(userId, request);
        return CommonResponse.onSuccess(PreferenceConverter.toUpdatePreferenceDTO(updatedPreference));
    }
}
