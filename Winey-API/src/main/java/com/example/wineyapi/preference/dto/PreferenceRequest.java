package com.example.wineyapi.preference.dto;

import com.example.wineydomain.common.model.Chocolate;
import com.example.wineydomain.common.model.Coffee;
import com.example.wineydomain.common.model.Fruit;
import lombok.Getter;

public class PreferenceRequest {

    @Getter
    public static class UpdatePreferenceDTO {
        private Chocolate chocolate;
        private Coffee coffee;
        private Fruit fruit;
    }
}
