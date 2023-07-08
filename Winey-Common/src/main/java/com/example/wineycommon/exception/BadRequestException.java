package com.example.wineycommon.exception;

import lombok.Getter;

import static com.example.wineycommon.exception.CommonResponseStatus.*;


@Getter
public class BadRequestException extends BaseException {
    private String message;

    public BadRequestException(String message) {
        super(_BAD_REQUEST);
        this.message = message;
    }

    public BadRequestException(CommonResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public BadRequestException(CommonResponseStatus errorCode) {
        super(errorCode);
    }
}
