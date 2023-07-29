package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;


@Getter
public class ForbiddenException extends BaseException {

    public ForbiddenException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
