package com.example.wineyapi.wine.dto;

import lombok.Getter;

public class WineRequest {

    @Getter
    public static class CreateWineDTO {
        private String field;
    }
}
