package com.example.wineycommon.exception;

public class OtherServerForbiddenException extends BaseException {

    public static final BaseException EXCEPTION = new OtherServerForbiddenException();

    private OtherServerForbiddenException() {
        super(CommonResponseStatus.OTHER_SERVER_FORBIDDEN);
    }
}
