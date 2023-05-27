package com.kafka.exception;

import com.kafka.domain.response.AppHttpCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SystemException extends RuntimeException {
    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCode httpCode) {
        super(httpCode.getMsg());
        this.code = httpCode.getCode();
        this.msg = httpCode.getMsg();
    }
}
