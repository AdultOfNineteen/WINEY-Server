package com.example.wineycommon.exception;

import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BaseDynamicException extends RuntimeException {
    BaseErrorCode status;
    Map<String,String> data;

    public BaseDynamicException(BaseErrorCode errorReason, HashMap<String, String> data) {
        this.status = errorReason;
        this.data = data;
    }


}
