package com.example.wineyapi.tastingNote.dto;

import com.example.wineycommon.annotation.Enum;
import com.example.wineydomain.common.model.BaseEntity;
import com.example.wineydomain.tastingNote.entity.SmellKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
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
    @NoArgsConstructor
    public static class CreateTastingNoteDTO {
        @NotNull(message = "와인 id 를 입력해주세요")
        @Schema(name = "wineId", required = true)
        private Long wineId;

        @Schema(name = "vintage", required = false)
        private Integer vintage;

        @Schema(name = "officialAlcohol", required = false)
        private Double officialAlcohol;

        @Schema(name = "price",description= "가격", required = false)
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

        private List<SmellKeyword> smellKeywordList;

        public Boolean getBuyAgain() {
            return buyAgain;
        }
    }

    @Getter
    public static class DeleteTastingNoteDTO {
        private String field;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @ToString
    public static class UpdateTastingNoteDTO {
        @Schema(name = "vintage", required = false)
        private Integer vintage;

        @Schema(name = "officialAlcohol", required = false)
        private Double officialAlcohol;

        @Schema(name = "price",description= "가격", required = false)
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

        @Schema(name = "smellKeywordList 추가사항 있을 경우 작성해주세요",description = "향 키워드 리스트", required = false)
        private List<SmellKeyword> smellKeywordList;

        @Schema(name = "deleteSmellKeywordList 삭제사항 있을 경우 작성해주세요",description = "향 키워드 리스트", required = false)
        private List<SmellKeyword> deleteSmellKeywordList;

        @Schema(name = "deleteImgLists 삭제사항 있을 경우 작성해주세요",description = "테이스팅 노트 이미지 리스트", required = false)
        private List<Long> deleteImgList;

        public Boolean getBuyAgain() {
            return buyAgain;
        }
    }
}
