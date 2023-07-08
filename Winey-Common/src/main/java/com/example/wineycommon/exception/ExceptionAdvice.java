package com.example.wineycommon.exception;

import com.example.wineycommon.reponse.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
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


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity onException(Exception exception, @AuthenticationPrincipal User user,
                                      HttpServletRequest request) {
        getExceptionStackTrace(exception, user, request);
        return new ResponseEntity<>(CommonResponse.onFailure("500", exception.getMessage(), null), null,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
