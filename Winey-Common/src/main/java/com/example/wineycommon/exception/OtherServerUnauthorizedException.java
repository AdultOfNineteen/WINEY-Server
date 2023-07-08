package com.example.wineycommon.exception;

public class OtherServerUnauthorizedException extends BaseException {

    public static final BaseException EXCEPTION = new OtherServerUnauthorizedException();

    private OtherServerUnauthorizedException() {
        super(CommonResponseStatus.OTHER_SERVER_UNAUTHORIZED);
    }
}
