package com.hansung.customblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

// application/octet-stream 타입의 Http 메시지에 대한 읽기 작업(Http 메시지 -> Java class)을 수행할 커스텀 메시지 컨버터
// 이 때 AbstractJackson2HttpMessageConverter를 상속해준 이유는 이를 통하면 Jackson 라이브러리 기반으로 문자열 형태의 json을 클래스로 변환해줄 수 있는데
// 현재 서버 측에서 문제가 되는 경우는 Multipart/form-data 이하의 application/json 타입이 누락되는 것이기 때문
@Component
public class OctetStreamReadMsgConverter extends AbstractJackson2HttpMessageConverter {
    @Autowired
    public OctetStreamReadMsgConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    // 기존 application/octet-stream 타입을 쓰기로 다루는 메시지 컨버터가 이미 존재 (ByteArrayHttpMessageConverter)
    // 따라서 해당 컨버터는 쓰기 작업에는 이용하면 안됨
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
