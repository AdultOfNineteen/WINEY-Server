package com.example.wineycommon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {

    HttpStatus httpStatus;
    CommonResponseStatus status;
    String responseMessage;
    Object result;
    Map<String, String> data;


    public BaseException(CommonResponseStatus status) {
        super();
        this.status = status;
        this.responseMessage = status.getMessage();
        this.httpStatus=status.getHttpStatus();
    }

    public BaseException(CommonResponseStatus status, Map<String, String> data) {
        super();
        this.status = status;
        this.responseMessage = status.getMessage();
        this.httpStatus=status.getHttpStatus();
        this.data = data;
    }



}
