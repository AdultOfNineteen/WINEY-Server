package com.example.wineycommon.exception;

import com.example.wineycommon.reponse.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity onConstraintValidationException(ConstraintViolationException e) {
        Map<String, String> errors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                .reduce((first, second) -> second)
                                .get().toString(),
                        ConstraintViolation::getMessage
                ));
        return new ResponseEntity<>(CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러", errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("")
                ));
        return new ResponseEntity<>(CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러", errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        String error = "잘못된 입력 값 : " + e.getMessage();
        return new ResponseEntity<>(
                CommonResponse.onFailure("ILLEGAL_ARGUMENT", "잘못된 인자 에러", Collections.singletonMap("error", error)),
                null,
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleConversionFailedException(ConversionFailedException e) {
        String error = e.getValue() + "은(는) " + e.getTargetType() + " 필드에 대한 유효한 값이 아닙니다.";
        return new ResponseEntity<>(
                CommonResponse.onFailure("CONVERSION_ERROR", "변환 에러", Collections.singletonMap("error", error)),
                null,
                HttpStatus.BAD_REQUEST);
    }

    private void getExceptionStackTrace(Exception e, @AuthenticationPrincipal User user,
                                        HttpServletRequest request) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        pw.append("\n==========================!!!ERROR TRACE!!!==========================\n");
        pw.append("uri: " + request.getRequestURI() + " " + request.getMethod() + "\n");
        if (user != null) {
            pw.append("uid: " + user.getUsername() + "\n");
        }
        pw.append(e.getMessage());
        pw.append("\n==================================================================\n");
        log.error(sw.toString());
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity onKnownException(BaseException baseException,
                                           @AuthenticationPrincipal User user, HttpServletRequest request) {
        getExceptionStackTrace(baseException, user, request);
        return new ResponseEntity<>(CommonResponse.onFailure(baseException.getStatus().getCode(), baseException.getResponseMessage(), baseException.getData()),
                null, baseException.getHttpStatus());
    }

// TODO : 최상위 예외라서 다른 핸들러들이 작동하지 않음.
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity onException(Exception exception, @AuthenticationPrincipal User user,
//                                      HttpServletRequest request) {
//        getExceptionStackTrace(exception, user, request);
//        return new ResponseEntity<>(CommonResponse.onFailure("500", exception.getMessage(), null), null,
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
