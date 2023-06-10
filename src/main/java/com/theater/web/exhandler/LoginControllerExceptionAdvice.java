package com.theater.web.exhandler;

import com.theater.web.login.LoginController;
import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {LoginController.class})
@Slf4j
public class LoginControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseResult> loginExHandler(Exception e) {
        log.error("[loginExHandler] ex = {}", e);
        HttpStatus status;
        Integer errorCode = null;

        if (e instanceof IllegalAccessException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = -111;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.info("[loginExHandler] status={}, errorCode={}", status, errorCode);
        ErrorResult errorResult = new ErrorResult(e.getMessage(), errorCode);
        return new ResponseEntity(errorResult, status);
        /**
         * rest api가 통신이 되는 경우는 status를 200으로 보내야지
         * 안그러면 front가 못 받고 뱉어냄
         */
    }

}
