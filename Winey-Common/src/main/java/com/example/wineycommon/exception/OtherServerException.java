package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class OtherServerException extends BaseException{
    public OtherServerException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
