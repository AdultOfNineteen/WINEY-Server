package com.example.wineyapi.security;

import static com.example.wineydomain.user.exception.UserAuthErrorCode.*;

import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineydomain.user.exception.UserAuthErrorCode;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.ServerException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServerException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception = (String) request.getAttribute("exception");

        UserAuthErrorCode errorCode;

        if(exception == null) {
            errorCode = UNAUTHORIZED_EXCEPTION;
            setResponse(response, errorCode, request);
            return;
        }

        switch (exception) {
            case "NotExistUser":
                errorCode = NOT_EXIST_USER;
                setResponse(response, errorCode, request);
                return;
            case "ExpiredJwtException":
                errorCode = EXPIRED_JWT_EXCEPTION;
                setResponse(response, errorCode, request);
                return;
            case "MalformedJwtException":
                errorCode = INVALID_TOKEN_EXCEPTION;
                setResponse(response, errorCode, request);
                return;
            case "HijackException":
                errorCode = HIJACK_JWT_TOKEN_EXCEPTION;
                setResponse(response, errorCode, request);
                return;
            case "NoSuchElementException":
                errorCode = NOT_EXISTS_USER_HAVE_TOKEN;
                setResponse(response, errorCode, request);
                return;
            case "NotUserActiveException":
                errorCode = NOT_USER_ACTIVE;
                setResponse(response, errorCode, request);
                return;
        }
    }

    private void setResponse(HttpServletResponse response, UserAuthErrorCode errorCode, HttpServletRequest request) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            json.put("code", errorCode.getCode());
            json.put("message", errorCode.getMessage());
            json.put("isSuccess",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String requestUri = request.getRequestURI();
        if(!requestUri.equals("/error")) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.append("\n==========================!!!JWT ERROR TRACE!!!==========================\n");
            pw.append("REQUEST_URI : ").append(request.getRequestURI()).append(" ").append(request.getMethod()).append("\n");
            pw.append("ERROR_CODE : ").append(errorCode.getCode()).append("\n");
            pw.append("ERROR_MESSAGE : ").append(errorCode.getMessage()).append("\n");
            pw.append("=========================================================================");
            log.error(sw.toString());
        }
        response.getWriter().print(json);
    }
}