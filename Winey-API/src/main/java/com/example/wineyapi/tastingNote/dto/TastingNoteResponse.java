package com.example.wineyapi.tastingNote.dto;

import com.example.wineydomain.wine.entity.Country;
import com.example.wineydomain.wine.entity.Wine;
import com.example.wineydomain.wine.entity.WineType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class TastingNoteResponse {

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class TastingNoteListDTO {
        @Schema(description = "테이스팅 노트 id")
        private Long noteId;
        @Schema(description = "테이스팅 노트 NO")
        private Integer tastingNoteNo;
        @Schema(description = "와인 이름")
        private String wineName;
        @Schema(description = "와인 생산지")
        private Country country;
        @Schema(description = "품종")
        private String varietal;
        @Schema(description = "평점")
        private Integer starRating;
        @Schema(description = "재구매 유무")
        private boolean buyAgain;
        @Schema(description = "와인 타입 RED,WHITE 등등")
        private WineType wineType;
        @Schema(description = "공개유무")
        private boolean isPublic;
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
        private Long tastingNoteId;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class DeleteTastingNoteDTO {
        private String field;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class TastingNoteDTO {
        @Schema(name = "noteId")
        private Long noteId;

        @Schema(description = "테이스팅 노트 NO")
        private Integer tastingNoteNo;

        @Schema(description = "작성 날짜")
        private String noteDate;

        @Schema(description = "와인 타입 RED, WHITE 등등")
        private WineType wineType;

        @Schema(description = "와인 이름")
        private String wineName;

        @Schema(description = "와인 생상지")
        private String region;

        @Schema(description = "별점")
        private Integer star;

        @Schema(description = "빈티지")
        private Integer vintage;

        @Schema(description = "사용자가 지정한 색")
        private String color;

        @Schema(description = "재구매 의사")
        private boolean buyAgain;

        @Schema(description = "varietal")
        private String varietal;

        @Schema(description = "사용자가 입력한 알코올 도수")
        private Double officialAlcohol;

        @Schema(description = "사용자가 입력한 가격")
        private Integer price;

        private List<String> smellKeywordList;

        @Schema(description = "내가 느낀 와인의 맛")
        private MyWineTaste myWineTaste;

        @Schema(description = "와인의 기본맛")
        private DefaultWineTaste defaultWineTaste;

        private List<TastingNoteImage> tastingNoteImage;

        private String memo;

        @Schema(description = "공개유무")
        private boolean isPublic;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class TastingNoteImage{
        private Long imgId;

        private String imgUrl;
    }
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class MyWineTaste{
        private double sweetness;

        private double acidity;

        private double alcohol;

        private double body;

        private double tannin;

        private double finish;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class DefaultWineTaste{
        private double sweetness;

        private double acidity;

        private double body;

        private double tannin;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class TasteAnalysisDTO {
        @Schema(description = "추천 국가")
        private String recommendCountry;

        @Schema(description = "추천 품종")
        private String recommendVarietal;

        @Schema(description = "추천 와인타입")
        private String recommendWineType;

        @Schema(description = "총 마신 와인 병수")
        private int totalWineCnt;

        @Schema(description = "재구매 의사가 있는 와인")
        private int buyAgainCnt;

        @Schema(description = "key = country, value = percent 식인 List 입니다.")
        private List<Top3Type> top3Type;

        @Schema(description = "key = country, value = percent 식인 List 입니다.")
        private List<Top3Country> top3Country;

        @Schema(description = "key = varietal, value = percent 식인 List 입니다.")
        private List<Top3Varietal> top3Varietal;

        @Schema(description = "key = smell, value = percent 식인 List 입니다. ")
        private List<Top7Smell> top7Smell;

        private Taste taste;

        @Schema(description = "평균 와인 가격")
        private int avgPrice;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Taste{
        private double sweetness;

        private double acidity;

        private double alcohol;

        private double body;

        private double tannin;

        private double finish;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top3Country{
        private String country;

        private int count;
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top3Type{
        private String type;

        private int percent;
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top3Varietal{
        private String varietal;

        private int percent;
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Top7Smell{
        private String smell;

        private int percent;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CheckTastingNote {
        private boolean tastingNoteExists;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class NoteFilterDTO {
        private List<WineTypeFilter> wineTypes;

        private List<CountryFilter> countries;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class WineTypeFilter{
        private String type;

        private String count;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class CountryFilter{
        private String country;

        private String count;
    }
}
