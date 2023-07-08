package com.example.wineycommon.exception;

import lombok.Getter;

import static com.example.wineycommon.exception.CommonResponseStatus.*;


@Getter
public class ForbiddenException extends BaseException {
    private String message;

    public ForbiddenException(String message) {
        super(_BAD_REQUEST);
        this.message = message;
    }

    public ForbiddenException(CommonResponseStatus errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public ForbiddenException(CommonResponseStatus errorCode) {
        super(errorCode);
    }

}
