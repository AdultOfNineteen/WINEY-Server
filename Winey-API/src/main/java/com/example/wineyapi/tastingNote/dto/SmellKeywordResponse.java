package com.example.wineyapi.tastingNote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SmellKeywordResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class SmellKeywordDTO {
        private String field;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class SmellKeywordListDTO {
        private List<SmellKeywordDTO> smellKeywordList;
        private Integer size;
    }
}
