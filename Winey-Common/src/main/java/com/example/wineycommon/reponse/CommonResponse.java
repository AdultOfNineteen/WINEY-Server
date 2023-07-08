package com.example.wineycommon.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
@ApiModel(value = "기본 응답")
public class CommonResponse<T> {

    @Schema(description = "성공 유무", required = true, example = "true")
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    @Schema(description = "응답 메시지", required = true, example = "요청에 성공하였습니다.")
    private final String message;
    @Schema(description = "응답 코드", required = true, example = "1000")
    private final String code;
    @Schema(description = "응답 결과", required = false, example = "응답 결과")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;



    // 요청에 성공한 경우11




    public static <T> CommonResponse<T> onSuccess(T data) {
        return new CommonResponse<>(true, "요청에 성공하였습니다.","1000", data);
    }

    public static <T> CommonResponse<T> onFailure(String code, String message, T data) {
        return new CommonResponse<>(false, message, code, data);
    }

}

