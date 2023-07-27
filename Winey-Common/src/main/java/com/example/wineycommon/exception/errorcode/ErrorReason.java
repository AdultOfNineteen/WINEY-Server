package com.example.wineycommon.exception.errorcode;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Builder
public class ErrorReason {
    private HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
    private final Map<String, String> result;

    public boolean getIsSuccess(){
        return isSuccess;
    }
}

