package com.hansung.customblog.config;

import com.hansung.customblog.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class BindingAdvice {

    // controller 패키지의 모든 Controller가 붙는 클래스의 인수 갯수는 상관 없이 모든 메소드에 적용
    @Around("execution(* com.hansung.customblog.controller..*Controller.*(..))")
    public Object vaildCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        System.out.println("type: " + type);
        System.out.println("method: " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg: args) {
            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }

                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
