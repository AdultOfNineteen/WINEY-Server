package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.WineType;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(name = "테이스팅 노트 id")
        private Long noteId;
        @Schema(name = "와인 이름")
        private String wineName;
        @Schema(name = "와인 생산지")
        private Country country;
        @Schema(name = "평점")
        private int starRating;
        @Schema(name = "재구매 유무")
        private boolean buyAgain;
        @Schema(name = "와인 타입 RED,WHITE 등등")
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
