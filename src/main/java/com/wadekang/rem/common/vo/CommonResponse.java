package com.wadekang.rem.common.vo;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private String message;
    private int code;
    private T data;
    private long time;

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = System.currentTimeMillis();
    }
}
