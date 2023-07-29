package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;


@Getter
public class UnauthorizedException extends BaseException {

    public UnauthorizedException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
