package com.example.wineyapi.preference.controller;

import com.example.wineyapi.preference.converter.PreferenceConverter;
import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineyapi.preference.dto.PreferenceResponse;
import com.example.wineyapi.preference.service.PreferenceService;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineydomain.preference.entity.Preference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "03-Preference\uD83C\uDF6B",description = "취향 관련 API")
@RestController
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @Deprecated
    @GetMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.PreferenceDTO> getPreference(@PathVariable Long userId) {
        return null;
    }

    @Operation(summary = "03-01 Preference\uD83C\uDF6B 취향 설정 #000_03_취향 입력 Made By Peter", description = "취향 설정 API입니다.")
    @PatchMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.UpdatePreferenceDTO> updatePreference(@PathVariable Long userId,
                                                                                   @RequestBody PreferenceRequest.UpdatePreferenceDTO request) {
        Preference updatedPreference = preferenceService.update(userId, request);
        return CommonResponse.onSuccess(PreferenceConverter.toUpdatePreferenceDTO(updatedPreference));
    }
}
