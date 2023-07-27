package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;


@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}