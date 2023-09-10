package com.example.wineyinfrastructure.amazonS3.exception;

import com.example.wineycommon.annotation.ExplainError;
import com.example.wineycommon.exception.errorcode.BaseErrorCode;
import com.example.wineycommon.exception.errorcode.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FileUploadException implements BaseErrorCode {

    /**
     * 잘못된 요청
     */
    FILE_UPLOAD_EXCEPTION(BAD_REQUEST, "FILE001", "파일 형식이 잘못되었습니다."),
    FILE_UPLOAD_NOT_EMPTY(BAD_REQUEST, "FILE002", "파일이 비어있습니다."),
    IMAGE_UPLOAD_ERROR(FORBIDDEN,"FILE003","파일 업로드에 실패했습니다."),
    IMAGE_DELETE_ERROR(INTERNAL_SERVER_ERROR, "PROJECT_IMG005","이미지 삭제 실패");;


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