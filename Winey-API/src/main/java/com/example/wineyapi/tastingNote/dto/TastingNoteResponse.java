package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class TastingNoteResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class TastingNoteListDTO {
        private Long noteId;

        private String wineName;

        private Country country;

        private int starRating;

        private boolean buyAgain;

        private WineType wineType;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateTastingNoteDTO {
        private Long tastingNoteId;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class UpdateTastingNoteDTO {
        private String field;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class DeleteTastingNoteDTO {
        private String field;
    }

    public static  class TastingNoteDTO {
    }
}
