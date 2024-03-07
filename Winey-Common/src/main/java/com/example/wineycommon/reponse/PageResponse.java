package com.example.wineycommon.reponse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@Schema(description = "페이징 처리 응답")
@ToString
public class PageResponse<T> {
    @Schema(description = "마지막 페이지 유무", required = true, example = "true")
    private final Boolean isLast;
    @Schema(description = "총 요소 갯수", required = true, example = "10")
    private final long totalCnt;
    @Schema(description = "요소", required = true)
    private final T contents;

    @JsonCreator
    public PageResponse(@JsonProperty("isLast") Boolean isLast,
        @JsonProperty("totalCnt") long totalCnt,
        @JsonProperty("contents") T contents) {
        this.isLast = isLast;
        this.totalCnt = totalCnt;
        this.contents = contents;
    }
}
