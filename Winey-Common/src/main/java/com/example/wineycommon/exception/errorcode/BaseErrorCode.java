package com.example.wineycommon.exception.errorcode;


public interface BaseErrorCode {
    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;

    public ErrorReason getErrorReasonHttpStatus();


}
