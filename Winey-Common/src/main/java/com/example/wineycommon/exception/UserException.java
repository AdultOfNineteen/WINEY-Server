package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class UserException extends BaseException {

    private String errorMessageWithSocialType;

    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(BaseErrorCode errorCode, String errorMessageWithSocialType) {
        super(errorCode);
        this.errorMessageWithSocialType = errorMessageWithSocialType;
    }
}
