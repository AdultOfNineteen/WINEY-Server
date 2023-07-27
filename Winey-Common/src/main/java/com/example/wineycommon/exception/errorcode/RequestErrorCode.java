package com.example.wineycommon.exception.errorcode;

import com.example.wineycommon.annotation.ExplainError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum RequestErrorCode implements BaseErrorCode{

    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST,"REQUEST_ERROR","요청 형식 에러 result 확인해주세요");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReason getErrorReason() {
        HashMap<String,String> data = new HashMap<>();
        data.put("field 에러가 나는 필드","필드에 대한 요청 형식에 대한 에러가 표시됩니다.");
        return ErrorReason.builder().message(message).code(code).isSuccess(false).result(data).build();
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
