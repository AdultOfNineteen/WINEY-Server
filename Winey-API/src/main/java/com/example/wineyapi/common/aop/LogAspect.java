package com.example.wineyapi.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.example.wineydomain.user.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAspect {
	@Pointcut("execution(* com.example.wineyapi..*Controller.*(..))")
	public void controller() {
	}

	@Pointcut("execution(* com.example.wineyapi..*Service.*(..))")
	public void service() {
	}

	@Before("controller()")
	public void beforeLogic(JoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String methodName = getMethodName(method);

		log.info("==========================LOG_START==========================");

		log.info("logging start method = {}", methodName);

		String[] parameterNames = methodSignature.getParameterNames();

		Object[] args = joinPoint.getArgs();
		int index = 0;
		for (Object arg : args) {
			if (arg instanceof User) {
				continue; // 다음 반복으로 넘어감
			}
			if (arg != null) {
				log.info("parameterNames = {} type = {}, value = {}", parameterNames[index],
					arg.getClass().getSimpleName(), arg.toString());
			}
			index += 1;
		}
	}

	private String getMethodName(Method method) {
		return method.getName();
	}

	@After("controller()")
	public void afterLogic(JoinPoint joinPoint) throws Throwable {
		Method method = getMethod(joinPoint);

		String methodName = getMethodName(method);

		log.info("logging finish method = {}", methodName);

		log.info("==========================LOG_FINISH==========================");
	}

	@AfterReturning(value = "controller()", returning = "returnObj")
	public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
		Method method = getMethod(joinPoint);

		String methodName = getMethodName(method);

		if (returnObj != null) {
			log.info("========================RETURN_LOG============================");
			log.info("method name = {}", methodName);
			log.info("return type = {}", returnObj.getClass().getSimpleName());
			log.info("return value = {}", returnObj.toString());
			log.info("==============================================================");
		}
	}

	private Method getMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		return signature.getMethod();
	}
}
