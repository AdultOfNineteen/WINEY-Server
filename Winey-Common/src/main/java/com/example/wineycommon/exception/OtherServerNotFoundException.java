package com.example.wineycommon.exception;

public class OtherServerNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new OtherServerNotFoundException();

    private OtherServerNotFoundException() {
        super(CommonResponseStatus.OTHER_SERVER_NOT_FOUND);
    }
}
