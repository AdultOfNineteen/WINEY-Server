package com.example.wineycommon.annotation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {EnumValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {
    String message() default "Enum 에 일치하는 값이 존재하지 않습니다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
