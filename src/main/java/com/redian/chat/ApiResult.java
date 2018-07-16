package com.redian.chat;

import java.io.Serializable;

public class ApiResult implements Serializable {
    protected int code = 200;
    protected Object data;
    protected String msg = "";

    public ApiResult(Object data) {
        this.data = data;
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ApiResult(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
