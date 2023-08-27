package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class MessageException extends BaseException{
    public MessageException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
