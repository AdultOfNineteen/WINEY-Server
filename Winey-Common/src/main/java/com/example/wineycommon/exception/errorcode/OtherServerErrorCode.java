package com.example.wineycommon.exception.errorcode;

import com.example.wineycommon.annotation.ExplainError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum OtherServerErrorCode implements BaseErrorCode{

    OTHER_SERVER_BAD_REQUEST(BAD_REQUEST, "FEIGN_400_1", "Other server bad request"),
    OTHER_SERVER_UNAUTHORIZED(BAD_REQUEST, "FEIGN_400_2", "Other server unauthorized"),
    OTHER_SERVER_FORBIDDEN(BAD_REQUEST, "FEIGN_400_3", "Other server forbidden"),
    OTHER_SERVER_EXPIRED_TOKEN(BAD_REQUEST, "FEIGN_400_4", "Other server expired token"),
    OTHER_SERVER_NOT_FOUND(BAD_REQUEST, "FEIGN_400_5", "Other server not found error"),
    OTHER_SERVER_INTERNAL_SERVER_ERROR(BAD_REQUEST, "FEIGN_400_6", "Other server internal server error"),
    OTHER_SERVER_METHOD_NOT_ALLOWED(BAD_REQUEST,"FEIGN_400_7" , "Other server method not allowed"),;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().message(message).code(code).isSuccess(false).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getMessage();
    }

    @Override
    public ErrorReason getErrorReasonHttpStatus(){
        return ErrorReason.builder().message(message).code(code).isSuccess(false).httpStatus(httpStatus).build();
    }
}
