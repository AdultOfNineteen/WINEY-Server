package com.example.wineycommon.exception;

public class OtherServerBadRequestException extends BaseException {

    public static final BaseException EXCEPTION = new OtherServerBadRequestException();

    private OtherServerBadRequestException() {
        super(CommonResponseStatus.OTHER_SERVER_BAD_REQUEST);
    }
}
