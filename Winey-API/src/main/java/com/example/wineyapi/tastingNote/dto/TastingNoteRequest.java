package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TastingNoteRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CreateTastingNoteDTO {
        @NotNull(message = "와인 id 를 입력해주세요")
        @Schema(name = "wineId", required = true)
        private Long wineId;

        @Schema(name = "vintage", required = true)
        @NotNull(message = "빈티지 를 입력해주세요")
        private Integer vintage;

        @NotNull(message = "공식 알코올 도수를 입력해주세요")
        @Schema(name = "officialAlcohol", required = true)
        private Double officialAlcohol;

        @Schema(name = "price",description= "가격", required = true)
        private Integer price;

        @NotNull(message = "색상 코드를 입력해주세요")
        @Schema(name ="color",description = "색상 코드", required = true)
        private String color;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name = "sweetness", description = "당도", required = true)
        private Integer sweetness;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name ="acidity",description = "산도", required = true)
        private Integer acidity;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name = "alcohol",description = "알코올", required = true)
        private Integer alcohol;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name = "body",description = "body", required = true)
        private Integer body;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name="tannin",description = "탄닌", required = true)
        private Integer tannin;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name="finish",description = "여운", required = true)
        private Integer finish;

        @NotNull(message = "느낌을 입력해주세요")
        @Schema(name ="memo",description = "느낌", required = true)
        private String memo;

        @NotNull(message = "재구매 유무를 입력해주세요")
        @Schema(name = "buyAgain",description = "재구매 유무", required = true)
        private Boolean buyAgain;

        @Min(value = 1 ,message = "1이상을 입력해주세요")
        @Max(value = 5 ,message = "5이하를 입력해주세요")
        @Schema(name ="rating", description = "평점", required = true)
        private Integer rating;

        @NotNull(message = "파일을 입력해주세요")
        @Schema(name = "multipartFiles")
        private List<MultipartFile> multipartFiles;

        @NotNull(message = "향 키워들 리스트를 입력해주세요")
        @Schema(name = "smellKeywordList",description = "향 키워드 리스트", required = true)
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
