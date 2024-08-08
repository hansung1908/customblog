package com.hansung.customblog.dto.response;

public class ResponseDto<T> {
    int status;
    T data;

    protected ResponseDto() {
    }

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }


}
