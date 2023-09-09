package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public class TastingNoteRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CreateTastingNoteDTO {
        @NotNull(message = "와인 id 를 입력해주세요")
        @Schema(name = "와인 id", required = true)
        private Long wineId;

        @NotNull(message = "빈티지를 입력해주세요")
        @Schema(name = "빈티지", required = true )
        private int vintage;

        @NotNull(message = "공식 알코올 도수를 입력해주세요")
        @Schema(name = "공식 알코올 도수", required = true)
        private double officialAlcohol;

        @NotNull(message = "가격을 입력해주세요")
        @Schema(name = "가격", required = true)
        private int price;

        @NotNull(message = "색상 코드를 입력해주세요")
        @Schema(name = "색상 코드", required = true)
        private String color;

        @NotNull(message = "당도를 입력해주세요")
        @Schema(name = "당도", required = true)
        private int sweetness;

        @NotNull(message = "산소를 입력해주세요")
        @Schema(name = "산도", required = true)
        private int acidity;

        @NotNull(message = "알코올을 입력해주세요")
        @Schema(name = "알코올", required = true)
        private int alcohol;

        @NotNull(message = "바디를 입력해주세요")
        @Schema(name = "Body", required = true)
        private int body;

        @NotNull(message = "탄닌을 입력해주세요")
        @Schema(name = "탄닌", required = true)
        private int tannin;

        @NotNull(message = "여운을 입력해주세요")
        @Schema(name = "여운", required = true)
        private int finish;

        @NotNull(message = "느낌을 입력해주세요")
        @Schema(name = "느낌", required = true)
        private String memo;

        @NotNull(message = "재구매 유무를 입력해주세요")
        @Schema(name = "재구매 유무", required = true)
        private boolean buyAgain;

        @NotNull(message = "평점을 입력해주세요")
        @Schema(name = "평점", required = true)
        private int rating;

        @NotNull(message = "파일을 입력해주세요")
        private List<MultipartFile> multipartFiles;

        @NotNull(message = "향 키워들 리스트를 입력해주세요")
        @Schema(name = "향 키워드 리스트", required = true)
        private List<SmellKeyword> smellKeywordList;

        public Boolean getBuyAgain() {
            return buyAgain;
        }
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
