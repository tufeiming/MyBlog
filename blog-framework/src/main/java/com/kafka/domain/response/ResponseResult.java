package com.kafka.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult() {
        this.code = AppHttpCode.SUCCESS.getCode();
        this.msg = AppHttpCode.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        ResponseResult<T> result = new ResponseResult<>();
        return result.error(code, msg);
    }

    public static <T> ResponseResult<T> okResult() {
        return new ResponseResult<>();
    }

    public static <T> ResponseResult<T> okResult(int code, String msg) {
        ResponseResult<T> result = new ResponseResult<>();
        return result.ok(code, null, msg);
    }

    public static <T> ResponseResult<T> okResult(T data) {
        ResponseResult<T> result = setAppHttpCodeEnum(AppHttpCode.SUCCESS, AppHttpCode.SUCCESS.getMsg());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCode code) {
        return setAppHttpCodeEnum(code, code.getMsg());
    }

    public static <T> ResponseResult<T> errorResult(AppHttpCode code, String msg) {
        return setAppHttpCodeEnum(code, msg);
    }

    public static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCode code) {
        return okResult(code.getCode(), code.getMsg());
    }

    private static <T> ResponseResult<T> setAppHttpCodeEnum(AppHttpCode code, String msg) {
        return okResult(code.getCode(), msg);
    }

    public ResponseResult<T> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
