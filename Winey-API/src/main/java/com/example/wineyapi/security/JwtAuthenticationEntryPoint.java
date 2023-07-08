package com.example.wineyapi.security;

import com.example.wineycommon.exception.CommonResponseStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServerException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception = (String) request.getAttribute("exception");

        CommonResponseStatus errorCode;


        /**
         * 토큰이 없는 경우 예외처리
         */
        if(exception == null) {
            errorCode = CommonResponseStatus.UNAUTHORIZED_EXCEPTION;
            setResponse(response, errorCode);
            return;
        }

        /**
         * 토큰이 만료된 경우 예외처리
         */
        if(exception.equals("NotExistUser")){
            errorCode = CommonResponseStatus.NOT_EXIST_USER;
            setResponse(response, errorCode);
            return;
        }
        else if(exception.equals("ExpiredJwtException")) {
            errorCode = CommonResponseStatus.EXPIRED_JWT_EXCEPTION;
            setResponse(response, errorCode);
            return;
        }
        else if (exception.equals("MalformedJwtException")){
            errorCode= CommonResponseStatus.INVALID_TOKEN_EXCEPTION;
            setResponse(response,errorCode);
            return;
        }
        else if(exception.equals("HijackException")){
            errorCode =CommonResponseStatus.HIJACK_JWT_TOKEN_EXCEPTION;
            setResponse(response,errorCode);
            return;
        }
    }

    private void setResponse(HttpServletResponse response, CommonResponseStatus errorCode) throws IOException {
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

        response.getWriter().print(json);
    }
}