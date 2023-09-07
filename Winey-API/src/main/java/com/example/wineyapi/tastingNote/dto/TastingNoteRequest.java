package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TastingNoteRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CreateTastingNoteDTO {
        private int vintage;

        private double officialAlcohol;

        private int price;

        private String color;

        private int sweetness;

        private int acidity;

        private int alcohol;

        private int body;

        private int tannin;

        private int finish;

        private String feelings;

        private List<MultipartFile> multipartFiles;

        private boolean buyAgain;

        private int rating;
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
