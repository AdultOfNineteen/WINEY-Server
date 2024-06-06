package com.example.wineyapi.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.wineyapi.security.JwtFilter;
import com.example.wineycommon.exception.BaseDynamicException;
import com.example.wineycommon.exception.BaseException;
import com.example.wineycommon.exception.UserException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineycommon.reponse.CommonResponse;
import com.example.wineyinfrastructure.slack.service.SlackService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final SlackService slackService;

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
        return new ResponseEntity<>(CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러 result 확인해주세요", errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {

            String fieldName = fieldError.getField();
            String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
            if (errors.containsKey(fieldName)) {
                String existingErrorMessage = errors.get(fieldName);
                errorMessage = existingErrorMessage + ", " + errorMessage;
            }

            errors.put(fieldName, errorMessage);
        }

        return new ResponseEntity<>(CommonResponse.onFailure("REQUEST_ERROR", "요청 형식 에러 result 확인해주세요", errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity onMissingServletRequestParameterException(MissingRequestHeaderException e, HttpServletRequest request) {
        String error = "누락된 쿼리 파라미터 : " + e.getHeaderName();
        getExceptionStackTrace(e, null, request);
        return new ResponseEntity<>(CommonResponse.onFailure("REQUEST_ERROR", "필수 쿼리 파라미터 누락 에러", Collections.singletonMap("error", error)), HttpStatus.BAD_REQUEST);
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
        pw.append(e.toString());
        pw.append("\n=====================================================================");
        log.error(sw.toString());
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity onKnownException(BaseException baseException,
                                           @AuthenticationPrincipal User user, HttpServletRequest request) {
        getExceptionStackTrace(baseException, user, request);

        return new ResponseEntity<>(CommonResponse.onFailure(baseException.getErrorReasonHttpStatus().getCode(), baseException.getErrorReasonHttpStatus().getMessage(), baseException.getErrorReasonHttpStatus().getResult()),
                null, baseException.getErrorReasonHttpStatus().getHttpStatus());
    }

    @ExceptionHandler(value = BaseDynamicException.class)
    public ResponseEntity onKnownDynamicException(BaseDynamicException baseDynamicException, @AuthenticationPrincipal User user,
                                      HttpServletRequest request) {
        getExceptionStackTrace(baseDynamicException, user, request);
        return new ResponseEntity<>(CommonResponse.onFailure(baseDynamicException.getStatus().getErrorReason().getCode(), baseDynamicException.getStatus().getErrorReason().getMessage(), baseDynamicException.getData()), null,
                baseDynamicException.getStatus().getErrorReasonHttpStatus().getHttpStatus());
    }

    /**
     * 가입된 소셜 타입을 동적으로 알려주어야하는 예외가 있어서 핸들러를 따로 구성했습니다.
     * @param userException
     * @param user
     * @param request
     * @return
     */
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity onUserException(UserException userException,
                                          @AuthenticationPrincipal User user, HttpServletRequest request) {
        getExceptionStackTrace(userException, user, request);

        String errorMessage = userException.getErrorReasonHttpStatus().getMessage();

        // `errorMessageWithSocialType`를 사용하여 메시지를 구성
        if(userException.getErrorMessageWithSocialType() != null) {
            errorMessage = userException.getErrorMessageWithSocialType();
        }

        return new ResponseEntity<>(
                CommonResponse.onFailure(userException.getErrorReasonHttpStatus().getCode(), errorMessage, userException.getErrorReasonHttpStatus().getResult()),
                null, userException.getErrorReasonHttpStatus().getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity onException(Exception exception, @AuthenticationPrincipal User user,
        HttpServletRequest request) throws IOException {
        HttpServletRequest httpServletRequest = unwrapHttpServletRequest(request);

        final Long userId = user != null ? Long.valueOf(user.getUsername()) : null;

        getExceptionStackTrace(exception, user, httpServletRequest);
        log.error("INTERNAL_SERVER_ERROR", exception);

        CommonResponseStatus internalServerError = CommonResponseStatus._INTERNAL_SERVER_ERROR;
        if (userId == null) {
            slackService.sendMessage("로그인 되지 않은 유저", exception, httpServletRequest);
        } else {
            slackService.sendMessage(String.valueOf(userId), exception, httpServletRequest);
        }

        return new ResponseEntity<>(CommonResponse.onFailure(internalServerError.getCode(), internalServerError.getMessage(), null), null, internalServerError.getHttpStatus());
    }

    private HttpServletRequest unwrapHttpServletRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return request;
        } else if (request instanceof ResourceUrlEncodingFilter) {
            return new ContentCachingRequestWrapper(request);
        } else if (request != null){
            return new ContentCachingRequestWrapper(request);
        }
        else {
            return request;
        }
    }
}
