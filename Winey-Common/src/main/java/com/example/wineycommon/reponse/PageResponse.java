package com.example.wineycommon.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Schema(description = "페이징 처리 응답")
@ToString
public class PageResponse<T> {
    @Schema(description = "마지막 페이지 유무", required = true, example = "true")
    private final Boolean isLast;
    @Schema(description = "총 요소 갯수", required = true, example = "10")
    private final long totalCnt;
    @Schema(description = "요소", required = true)
    private final T contents;
}
