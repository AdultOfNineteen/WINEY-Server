package com.example.wineyapi.tastingNote.dto;

import lombok.Getter;

public class TastingNoteRequest {

    @Getter
    public static class CreateTastingNoteDTO {
        private String field;
    }

    @Getter
    public static class UpdateTastingNoteDTO {
        private String field;
    }

    @Getter
    public static class DeleteTastingNoteDTO {
        private String field;
    }
}
