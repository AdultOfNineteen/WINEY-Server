package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class InternalServerException extends BaseException {
    public InternalServerException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
