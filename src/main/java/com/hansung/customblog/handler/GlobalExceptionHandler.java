package com.hansung.customblog.handler;

import com.hansung.customblog.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice   // 모든 exception을 이곳으로 오게함
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SQLException.class)
    public ResponseDto<String> handleSQLException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스에 문제가 발생하였습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseDto<String> handleRuntimeException(Exception e) {
        return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "런타임 에러가 발생하였습니다.");
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseDto<String> handleIOException(Exception e) {
        return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "입출력 오류가 발생하였습니다.");
    }
}
