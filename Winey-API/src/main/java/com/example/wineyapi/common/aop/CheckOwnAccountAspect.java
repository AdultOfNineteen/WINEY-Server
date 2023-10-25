package com.example.wineyapi.common.aop;

import com.example.wineyapi.user.service.UserService;
import com.example.wineycommon.exception.ForbiddenException;
import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.exception.UserAuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class CheckOwnAccountAspect {

    private final UserService userService;

    @Before("@annotation(com.example.wineyapi.common.annotation.CheckOwnAccount) && args(userId,..)")
    public void checkAccount(JoinPoint joinPoint, Long userId) {
        User currentUser = userService.getCurrentLoggedInUser(); // 현재 로그인한 사용자 가져오기

        // 인증정보가 없거나, 현재 요청한 사용자의 id와 파라미터로 넘긴 id가 다른경우
        if (currentUser == null || !currentUser.getId().equals(userId)) {
            throw new ForbiddenException(UserAuthErrorCode.NOT_ALLOWED_ACCESS);
        }
    }
}