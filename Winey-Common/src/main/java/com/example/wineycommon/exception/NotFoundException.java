package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends BaseException  {

    public NotFoundException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
