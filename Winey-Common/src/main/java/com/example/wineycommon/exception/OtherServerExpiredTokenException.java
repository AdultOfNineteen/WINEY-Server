package com.example.wineycommon.exception;

public class OtherServerExpiredTokenException extends BaseException {

    public static final BaseException EXCEPTION = new OtherServerExpiredTokenException();

    private OtherServerExpiredTokenException() {
        super(CommonResponseStatus.OTHER_SERVER_EXPIRED_TOKEN);
    }
}
