package com.theater.web.responsedata;

import lombok.Getter;

@Getter
public class ResponseResult {

    private String message;

    public ResponseResult(String message) {
        this.message = message;
    }

}
