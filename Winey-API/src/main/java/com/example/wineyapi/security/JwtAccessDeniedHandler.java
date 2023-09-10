package com.example.wineyapi.security;

import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        UserAuthErrorCode errorCode = UserAuthErrorCode.NOT_ALLOWED_ACCESS;

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject json = new JSONObject();
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