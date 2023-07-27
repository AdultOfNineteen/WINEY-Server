package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import com.example.wineycommon.exception.errorcode.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private BaseErrorCode errorCode;

    public ErrorReason getErrorReason() {
        return this.errorCode.getErrorReason();
    }

    public ErrorReason getErrorReasonHttpStatus(){
        return this.errorCode.getErrorReasonHttpStatus();
    }

}
