package com.hansung.customblog.config;

import com.hansung.customblog.dto.response.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class BindingAdvice {

    private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

    // controller 패키지의 모든 Controller가 붙는 클래스의 인수 갯수는 상관 없이 모든 메소드에 적용
    // handler 패키지도 감시 대상에 포함
    @Around("execution(* com.hansung.customblog.controller..*Controller.*(..)) || execution(* com.hansung.customblog.handler..*(..))")
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
                        // 로그 레벨: error < warn < info < debug (레벨이 클수록 하위 로그를 출력, 예: info 레벨이면 error, warn, info만 출력)
                        log.warn(type + "." + method + "() -> 필드: " + error.getField() + ", 메세지: " + error.getDefaultMessage());
                        // 로그를 파일로 생성하면 관리가 힘들어 진다.
                        log.debug(type + "." + method + "() -> 필드: " + error.getField() + ", 메세지: " + error.getDefaultMessage());
                    }

                    return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
