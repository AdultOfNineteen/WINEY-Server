package com.example.wineyapi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API를 요청한 user와 pathVariable로 넘긴 userId가 같은지 확인할 때 사용합니다.
 * ex) 본인의 계정만 삭제할 수 있다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckOwnAccount {
}
