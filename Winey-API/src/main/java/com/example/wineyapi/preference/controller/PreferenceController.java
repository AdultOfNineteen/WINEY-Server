package com.example.wineyapi.preference.controller;

import com.example.wineyapi.preference.dto.PreferenceRequest;
import com.example.wineyapi.preference.dto.PreferenceResponse;
import com.example.wineycommon.reponse.CommonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class PreferenceController {

    @GetMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.PreferenceDTO> getPreference(@PathVariable Long userId) {
        return null;
    }

    @PatchMapping("/users/{userId}/preferences")
    public CommonResponse<PreferenceResponse.UpdatePreferenceDTO> updatePreference(@PathVariable Long userId,
                                                                                @RequestBody PreferenceRequest.UpdatePreferenceDTO request) {
        return null;
    }
}
