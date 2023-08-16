package com.example.wineyapi.common.aop;

import com.example.wineycommon.exception.NotFoundException;
import com.example.wineycommon.exception.errorcode.CommonResponseStatus;
import com.example.wineydomain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class CheckIdExistenceAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.example.wineyapi.common.annotation.CheckIdExistence)")
    public void checkIdExistence(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if ("userId".equals(parameterNames[i])) {
                Long userId = (Long) args[i];
                if (!userRepository.existsById(userId)) {
                    throw new NotFoundException(CommonResponseStatus.NOT_EXIST_USER);
                }
            }
        }
    }
}