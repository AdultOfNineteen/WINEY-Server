package com.example.wineydomain.tastingNote.exception;

import com.example.wineycommon.annotation.ExplainError;
import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import com.example.wineycommon.exception.errorcode.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Getter
@AllArgsConstructor
public enum GetTastingNoteErrorCode implements BaseErrorCode {
    /*
       인증 관련 에러코드
    */

    @ExplainError("해당 와인이 없는 경우")
    NOT_FOUND_TASTING_NOTE(BAD_REQUEST,"TASTING_NOTE_001", "해당 테이스팅 노트가 존재하지 않습니다.."),
    @ExplainError("해당 테이스팅 노트가 공개가 아닌 경우")
    NOT_PUBLIC_TASTING_NOTE(BAD_REQUEST,"TASTING_NOTE_002", "해당 테이스팅 노트가 공개되지 않았습니다.");


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
