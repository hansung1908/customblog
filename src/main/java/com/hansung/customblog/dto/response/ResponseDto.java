package com.hansung.customblog.dto.response;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int status;
    private T data;

    protected ResponseDto() {
    }

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }


}
